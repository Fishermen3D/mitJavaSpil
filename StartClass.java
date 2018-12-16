package org.tut.spil;

//de biblioteker som vi vil bruge til spillet
import java.util.Random;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartClass extends ApplicationAdapter {
	
	//den vil tegne vores ting på vinduet
	SpriteBatch sb;
	
	//spilleren vi kan styre
	Sprite spiller;
	
	//den hastighed vores spiller skal gå i.
	float hastighed = 250;
	
	//frugten vi skal gribe
	Sprite frugt;
	
	//den hastighed vores frugt skal falde
	float frugtHastighed = 100;
	
	//selve baggrunden
	Sprite baggrund;
	
	//den lyd effekt som vi vil have afspillet når spilleren fanger frugten
	Sound lyd1;
	
	//den lyd effekt som vi vil have afspillet når frugten rammer bunden af vinduet 
	Sound lyd2;
	
	//denne variable vil vi bruge til at fortælle computeren at spillet er slut
	boolean gameOver = false;
	
	//det her bliver vores game over skærm
	Sprite slut;
	
	@Override
	public void create () {
		/*------------------------------------------------------------------
		 * Herinde vil vi oprette alle vores ting som skal bruges i spillet *
		 * Alt herinde vil kund blive kaldt en gang                         *
		 -------------------------------------------------------------------*/
		
		//sæt vindue størelsen til at være 800 gange 600
		Gdx.graphics.setWindowedMode(800, 600);
		
		//sæt titlen på vinduet
		Gdx.graphics.setTitle("Mit Første spil!");
		
		//vi vil ikke have at spilleren kan udvide vinduet
		Gdx.graphics.setResizable(false);
		
		//her opretter vi vores tegner
		sb = new SpriteBatch();
		
		//her opretter vi baggrunden med en ny tekstur
		baggrund = new Sprite(new Texture("baggrund.png"));
		
		//her opretter vi spilleren med en ny tekstur
		spiller = new Sprite(new Texture("mand.png"));
		
		//her sætter vi spillerens position.
		spiller.setPosition(10, 10);
		
		//her opretter vi vores frugt med en ny tekstur
		frugt = new Sprite(new Texture("frugt.png"));
		
		//lad os placere frugten på toppen af skærmen
		frugt.setPosition(100, 600);
		
		//her opretter vi game over skærmen
		slut = new Sprite(new Texture("gameOver.png"));
		
		//den lyd vi vil afspille når spilleren fanger frugten
		lyd1 = Gdx.audio.newSound(Gdx.files.internal("lyd/ding1.wav"));
		
		//den lyd der vil blive afspillet når frugten rammer bunden af vinduet
		lyd2 = Gdx.audio.newSound(Gdx.files.internal("lyd/ding2.wav"));
	}

	@Override
	public void render () {
		/*--------------------------------------------------------------------------------
		 * Her vil vi skrive hvordan spillet virker og hvordan spilleren skal spille det *
		 * Alt kode herinde vil blive kaldt 60 gange i sekundet.                         *
		 --------------------------------------------------------------------------------*/
		
		//den hastighed det tog computeren at tegne den sidste frame
		float deltaTid = Gdx.graphics.getDeltaTime();
		
		//hvis spilleren trykker venstre pil
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			
			//så flytter vi spilleren til venstre
			spiller.setPosition(spiller.getX() - hastighed * deltaTid, spiller.getY());
			
		}
		
		//hvis spilleren trykker højre pil
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			
			//så flytter vi spilleren til højre
			spiller.setPosition(spiller.getX() + hastighed * deltaTid, spiller.getY());
			
		}
		
		//så længe spillet ikke er slut
		if(gameOver == false) {
			
			//så lader vi frugten falde ned
			frugt.setPosition(frugt.getX(), frugt.getY() - hastighed * deltaTid);
			
		}
		
		
		//hvis frugten rammer bunden af vinduet.
		if(frugt.getY() < 0) {
			
			//lad os placere frugten væk fra bunden for en sikkerheds skyld
			frugt.setPosition(100, 600);
			
			//afspil en lyd
			lyd2.play();
			
			//nu er spillet slut. øv bøv
			gameOver = true;
		}
		
		//hvis frugten rammer spilleren
		if(frugt.getBoundingRectangle().overlaps(spiller.getBoundingRectangle())) {
			
			//denne variable laver tilfældige tal
			Random etEllerAndetTal = new Random();
			
			//denne variable vil tage et tilfældigt tal i mellem 0 til 800 fra variablen etEllerAndetTal
			int nytTal = etEllerAndetTal.nextInt(800);
			
			//lad os nu placere vores frugt på toppen af vinduet med det nye tilfældige tal.
			frugt.setPosition(nytTal, 600);
			
			//afspil en lyd
			lyd1.play();
			
		}
		
		//hvis spilleren har lyst til at spille igen
		//så lad computeren tjekke om spilleren trykker på tasten enter
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			
			//så starter vi spillet igen
			gameOver = false;
			
		}
		
		//begynd at tegne vores ting
		sb.begin();
		
		//tegn baggrunden
		baggrund.draw(sb);
		
		//tegn spilleren
		spiller.draw(sb);
		
		//tegn frugten
		frugt.draw(sb);
		
		//hvis spillet er slut
		if(gameOver == true) {
			
			//så tegner vi game over skærmen
			slut.draw(sb);
			
		}
		
		//hold op med at tegne vores ting
		sb.end();
		
	}
	
	@Override
	public void dispose () {
		
		/*---------------------------------------------------------------------------------------------------
		 * Alt herinde er noget computeren selv sørger for.                                                   *
		 * Når computeren er færdig med noget af det data vi har givet den vil den fjerne det fra hukommelsen *
		 -----------------------------------------------------------------------------------------------------*/
		
		sb.dispose();
		lyd1.dispose();
		lyd2.dispose();
		
	}
}
