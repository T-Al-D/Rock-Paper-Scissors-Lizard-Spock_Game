package de.uniks.pmws2021.rps.model;
import java.util.Objects;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Player
{
   public static final String PROPERTY_PLAYER_NAME = "playerName";
   public static final String PROPERTY_CURRENT_MOVE = "currentMove";
   public static final String PROPERTY_WINNINGS = "winnings";
   public static final String PROPERTY_GAME = "game";
   public static final String PROPERTY_VICTORY = "victory";
   private String playerName;
   private String currentMove;
   private int winnings;
   protected PropertyChangeSupport listeners;
   private Game game;
   private Boolean victory;

   public String getPlayerName()
   {
      return this.playerName;
   }

   public Player setPlayerName(String value)
   {
      if (Objects.equals(value, this.playerName))
      {
         return this;
      }

      final String oldValue = this.playerName;
      this.playerName = value;
      this.firePropertyChange(PROPERTY_PLAYER_NAME, oldValue, value);
      return this;
   }

   public String getCurrentMove()
   {
      return this.currentMove;
   }

   public Player setCurrentMove(String value)
   {
      if (Objects.equals(value, this.currentMove))
      {
         return this;
      }

      final String oldValue = this.currentMove;
      this.currentMove = value;
      this.firePropertyChange(PROPERTY_CURRENT_MOVE, oldValue, value);
      return this;
   }

   public int getWinnings()
   {
      return this.winnings;
   }

   public Player setWinnings(int value)
   {
      if (value == this.winnings)
      {
         return this;
      }

      final int oldValue = this.winnings;
      this.winnings = value;
      this.firePropertyChange(PROPERTY_WINNINGS, oldValue, value);
      return this;
   }

   public Game getGame()
   {
      return this.game;
   }

   public Player setGame(Game value)
   {
      if (this.game == value)
      {
         return this;
      }

      final Game oldValue = this.game;
      if (this.game != null)
      {
         this.game = null;
         oldValue.withoutPlayers(this);
      }
      this.game = value;
      if (value != null)
      {
         value.withPlayers(this);
      }
      this.firePropertyChange(PROPERTY_GAME, oldValue, value);
      return this;
   }

   public Boolean getVictory()
   {
      return this.victory;
   }

   public Player setVictory(Boolean value)
   {
      if (Objects.equals(value, this.victory))
      {
         return this;
      }

      final Boolean oldValue = this.victory;
      this.victory = value;
      this.firePropertyChange(PROPERTY_VICTORY, oldValue, value);
      return this;
   }

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (this.listeners != null)
      {
         this.listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public boolean addPropertyChangeListener(PropertyChangeListener listener)
   {
      if (this.listeners == null)
      {
         this.listeners = new PropertyChangeSupport(this);
      }
      this.listeners.addPropertyChangeListener(listener);
      return true;
   }

   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      if (this.listeners == null)
      {
         this.listeners = new PropertyChangeSupport(this);
      }
      this.listeners.addPropertyChangeListener(propertyName, listener);
      return true;
   }

   public boolean removePropertyChangeListener(PropertyChangeListener listener)
   {
      if (this.listeners != null)
      {
         this.listeners.removePropertyChangeListener(listener);
      }
      return true;
   }

   public boolean removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      if (this.listeners != null)
      {
         this.listeners.removePropertyChangeListener(propertyName, listener);
      }
      return true;
   }

   @Override
   public String toString()
   {
      final StringBuilder result = new StringBuilder();
      result.append(' ').append(this.getPlayerName());
      result.append(' ').append(this.getCurrentMove());
      return result.substring(1);
   }

   public void removeYou()
   {
      this.setGame(null);
   }
}
