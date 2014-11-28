package co.s4n.sa;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SocialWeb20Adapter {

	private static final Random rnd = new Random();

	public List<String> twitterFollowers() throws InterruptedException, IOException, SQLException {
		System.out.println("Twitter!!");
		Random random = new Random(System.currentTimeMillis());
		int randomNumber = random.nextInt(9) % 3;
		System.out.println("el random es " + randomNumber);
		if(randomNumber == 0){
			throw new IOException("Twitter id not respond");
		}
		
		if(randomNumber == 1){
			throw new SQLException("Foreing key violation exception");
		}
		
		Thread.sleep(rnd.nextInt(1000));
		System.out.println("Twitter!!");
		List<String> amigos = new ArrayList<String>();
		amigos.add( "TwitterFollower" );
		return amigos;
		
	}

	public List<String> facebookFriends() throws InterruptedException {
		Thread.sleep(rnd.nextInt(1000));
		System.out.println("Facebook!!");
		List<String> amigos = new ArrayList<String>();
		amigos.add( "FacebookFriend" );		
		return amigos;
	}

	public List<String> gmailContacts() throws InterruptedException {
		Thread.sleep(rnd.nextInt(1000));
		System.out.println("Gmail!!");
		List<String> amigos = new ArrayList<String>();
		amigos.add( "GmailContact" );		
		return amigos;
	}

	public List<String> instagramFollowers() throws InterruptedException {
		Thread.sleep(rnd.nextInt(3000));
		List<String> amigos = new ArrayList<String>();
		amigos.add( "InstagramFollower" );		
		return amigos;
	}
}
