package carrot.game.player.sample;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class Aiueo implements JankenPlayer
{

	public Random random;
	private int myscore;
	
	@Override
	public void newGame() 
	{
		this.random = new Random();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) 
	{
		switch(currentMatchStatus.ownScore-myscore)
		{
		case 2:
			myscore=currentMatchStatus.ownScore;
			return JankenHand.GU;
			
		case 4:
			myscore=currentMatchStatus.ownScore;
			return JankenHand.CHOKI;
			
		case 5:
			myscore=currentMatchStatus.ownScore;
			return JankenHand.PA;
			
		default:
		    myscore=currentMatchStatus.ownScore;
		    return random();		
		}
	}
			
			
	public JankenHand random()
	{
		double y=Math.random();
		
		 if(y<0.7)
		    {
		    	return JankenHand.CHOKI;
		    }
		 else if(y<0.9&&y>0.5)
			{
				return JankenHand.PA;
			}
		 else
			{
				return JankenHand.GU;
			}
		}
	}



