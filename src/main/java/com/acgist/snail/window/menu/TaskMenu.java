package com.acgist.snail.window.menu;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acgist.snail.context.SystemAsynContext;
import com.acgist.snail.module.config.DownloadConfig;
import com.acgist.snail.pojo.entity.TaskEntity.Type;
import com.acgist.snail.utils.ClipboardUtils;
import com.acgist.snail.utils.FileUtils;
import com.acgist.snail.utils.FileVerifyUtils;
import com.acgist.snail.window.AlertWindow;
import com.acgist.snail.window.main.MainWindow;
import com.acgist.snail.window.torrent.TorrentWindow;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * 菜单 - 任务
 */
/**
 * @author 28954
 *
 */
public class TaskMenu extends ContextMenu {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskMenu.class);
	
	private static TaskMenu INSTANCE;
	
	private TaskMenu() {
		createMenu();
	}

	static {
		synchronized (TaskMenu.class) {
			if (INSTANCE == null) {
				LOGGER.info("初始化任务菜单");
				INSTANCE = new TaskMenu();
				INSTANCE.setOpacity(0.94);
				INSTANCE.getStyleClass().add("taskMenu");
			}
		}
	}
	
	public static final TaskMenu getInstance() {
		if(MainWindow.getInstance().controller().hasTorrent()) {
			INSTANCE.torrentMenu.setDisable(false);
			INSTANCE.exportTorrentMenu.setDisable(false);
		} else {
			INSTANCE.torrentMenu.setDisable(true);
			INSTANCE.exportTorrentMenu.setDisable(true);
		}
		return INSTANCE;
	}

	private MenuItem startMenu;
	private MenuItem pauseMenu;
	private MenuItem deleteMenu;
	private MenuItem copyUrlMenu;
	private MenuItem torrentMenu;
	private MenuItem exportTorrentMenu;
	private MenuItem verifyMenu;
	private MenuItem openFolderMenu;
	
	/**
	 * 创建菜单
	 */
	private void createMenu() {
		this.startMenu = new MenuItem("开始", new ImageView("/image/16/start.png"));
		this.pauseMenu = new MenuItem("暂停", new ImageView("/image/16/pause.png"));
		this.deleteMenu = new MenuItem("删除", new ImageView("/image/16/delete.png"));
		this.copyUrlMenu = new MenuItem("复制地址", new ImageView("/image/16/download.png"));
		this.torrentMenu = new MenuItem("文件选择", new ImageView("/image/16/edit.png"));
		this.exportTorrentMenu = new MenuItem("导出种子", new ImageView("/image/16/export.png"));
		this.verifyMenu = new MenuItem("文件校验", new ImageView("/image/16/verify.png"));
		this.openFolderMenu = new MenuItem("打开目录", new ImageView("/image/16/folder.png"));
		
		startMenu.setOnAction(startEvent);
		pauseMenu.setOnAction(pauseEvent);
		deleteMenu.setOnAction(deleteEvent);
		copyUrlMenu.setOnAction(copyUrlEvent);
		torrentMenu.setOnAction(torrentEvent);
		exportTorrentMenu.setOnAction(exportTorrentEvent);
		verifyMenu.setOnAction(verifyEvent);
		openFolderMenu.setOnAction(openFolderEvent);
		
		this.getItems().add(startMenu);
		this.getItems().add(pauseMenu);
		this.getItems().add(deleteMenu);
		this.getItems().add(copyUrlMenu);
		this.getItems().add(torrentMenu);
		this.getItems().add(exportTorrentMenu);
		this.getItems().add(verifyMenu);
		this.getItems().add(openFolderMenu);
	}
	
	private EventHandler<ActionEvent> startEvent = (event) -> {
		MainWindow.getInstance().controller().start();
	};
	
	private EventHandler<ActionEvent> pauseEvent = (event) -> {
		MainWindow.getInstance().controller().pause();
	};
	
	private EventHandler<ActionEvent> deleteEvent = (event) -> {
		MainWindow.getInstance().controller().delete();
	};
	
	private EventHandler<ActionEvent> copyUrlEvent = (event) -> {
		MainWindow.getInstance().controller().selected()
		.forEach(wrapper -> {
			ClipboardUtils.copy(wrapper.entity().getUrl());
		});
	};
	
	private EventHandler<ActionEvent> torrentEvent = (event) -> {
		if(!MainWindow.getInstance().controller().hasTorrent()) {
			return;
		}
		MainWindow.getInstance().controller().selected()
		.forEach(wrapper -> {
			if(wrapper.entity().getType() == Type.torrent) {
				TorrentWindow.getInstance().controller().tree(wrapper);
			}
		});
		TorrentWindow.getInstance().show();
	};
	
	private EventHandler<ActionEvent> exportTorrentEvent = (event) -> {
		if(!MainWindow.getInstance().controller().hasTorrent()) {
			return;
		}
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("种子文件保存目录");
		DownloadConfig.lastPath(chooser);
		File file = chooser.showDialog(new Stage());
		if (file != null) {
			DownloadConfig.setLastPath(file.getPath());
			MainWindow.getInstance().controller().selected()
			.forEach(wrapper -> {
				if(wrapper.entity().getType() == Type.torrent) {
					String torrent = wrapper.entity().getTorrent();
					String fileName = FileUtils.fileNameFromUrl(torrent);
					String newFile = FileUtils.file(file.getPath(), fileName);
					FileUtils.copy(torrent, newFile);
				}
			});
		}
	};
	
	private EventHandler<ActionEvent> verifyEvent = (event) -> {
		SystemAsynContext.runasyn("File Verify Thread", () -> {
			Map<String, String> hash = new HashMap<>();
			MainWindow.getInstance().controller().selected()
			.forEach(wrapper -> {
				hash.putAll(FileVerifyUtils.sha1(wrapper.entity().getFile()));
			});
			Platform.runLater(() -> {
				AlertWindow.info("文件校验", hash.toString());
			});
		});
	};
	
	private EventHandler<ActionEvent> openFolderEvent = (event) -> {
		MainWindow.getInstance().controller().selected()
		.forEach(wrapper -> {
			FileUtils.openInDesktop(wrapper.downloadFolder());
		});
	};
	
}
