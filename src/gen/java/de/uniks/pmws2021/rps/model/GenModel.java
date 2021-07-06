package de.uniks.pmws2021.rps.model;

import org.fulib.builder.ClassModelDecorator;
import org.fulib.builder.ClassModelManager;
import org.fulib.builder.reflect.Link;

import java.util.ArrayList;

public class GenModel implements ClassModelDecorator
{
	class Player{
		String playerName ;
		String currentMove;
		int winnings;
		Boolean victory;
		@Link("players")
		Game game;
	}
	class Game{
		Boolean gameModeExtension;
		int maxRounds;
		int currentRound;
		boolean bestOf;
		@Link("game")
		ArrayList<Player> players;
	}
	@Override
	public void decorate(ClassModelManager mm)
	{
		mm.haveNestedClasses(GenModel.class);
	}
}