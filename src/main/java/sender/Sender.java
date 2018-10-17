package sender;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Sender {
	public static void main(String[] args) throws JMSException {
		/*if(args.length==0) {
			System.out.println("Must supply a message");
			System.out.println("From Maven run with: maven-Pargs=\"Hello World\" run");
			return;
		}else {
			System.out.println(args[0]);
		}*/
		ConnectionFactory connectionFactory;
		Connection connection=null;
		try {
			InitialContext initialContext =new InitialContext();
			Queue queue =(Queue) initialContext.lookup("jms/P2PQueue");
			connectionFactory=(QueueConnectionFactory) initialContext.lookup("jms/__defaultConnectionFactory");
			
			connection= connectionFactory.createConnection();
			Session session= connection.createSession( false, Session.AUTO_ACKNOWLEDGE);
			
			MessageProducer messageProducer =session.createProducer(queue);
			TextMessage textmessage=session.createTextMessage("hello");
			messageProducer.send(textmessage);
			System.out.println("Message Produced");
					
		}catch(NamingException e){
			e.printStackTrace();
		}catch(JMSException e){
			e.printStackTrace();
		}finally {
			if(connection!=null)try
			{
				connection.close();
			}catch (JMSException e) {
			e.printStackTrace();
			}
		}
	}

}
