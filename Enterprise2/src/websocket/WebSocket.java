package websocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import listener.CountListener;

@ServerEndpoint("/websocket")
public class WebSocket {

	private static Set<WebSocket> set = new HashSet<>();
	private Session session;


	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		set.add(this);
		try {
			this.session.getBasicRemote().sendText(String.valueOf(CountListener.numNow));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("建立新连接");

	}

	@OnClose
	public void onClose() {
		set.remove(this);
		
		System.out.println("关闭一连接");
	}

	@OnMessage
	public void onMessage(String message, Session session) {

	}

	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}
	
	public static void sendMessage(String message) {
		for (WebSocket w : set) {
			try {
				w.session.getBasicRemote().sendText(message);
			} catch (Exception e) {
				continue;
			}
		}
	}
}
