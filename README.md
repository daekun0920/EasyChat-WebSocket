# EasyChat-WebSocket v1.1 (released 2019-03-11)
EasyChat - Easy and Simple WebSocket Example

## Features
  - EasyChat is an inter-communicable one-to-one chatting program.
  - You can modify the source code to make it more suitable for your own product.
  - is written in Java, Javascript(jQuery).
  
## What has changed in v1.1?
  - Now the data is sent in JSON form.
  ```javascript
  function sendMessage() {
	var messageText = document.getElementById("message");

	messageText.value = messageText.value.replace('"', '&#34;');

	webSocket.send('{ \"sendTo\" : \"' + $("#sendTo").text() + '\", \"message\" : \"' + messageText.value + '\"}');
  }
  ```
  ```java
   @OnMessage
   public void handleMessage(String message,Session userSession) throws IOException{
    	JSONObject obj = new JSONObject(message);
    	
        //Send the message to selected user.
        Iterator<Session> iterator = sessionUsers.iterator();
        while(iterator.hasNext()){
        	Session sess = iterator.next();
        	
            if (sess.getUserProperties().containsKey(obj.getString("sendTo"))) {
            	sess.getBasicRemote().sendText(buildJsonData(obj.getString("sendTo"), obj.getString("message")));
            }
        }
  }
  ```
