package de.uniks.pmws2021.rps.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.Collection;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Game
{
   public static final String PROPERTY_GAME_MODE_EXTENSION = "gameModeExtension";
   public static final String PROPERTY_MAX_ROUNDS = "maxRounds";
   public static final String PROPERTY_CURRENT_ROUND = "currentRound";
   public static final String PROPERTY_BEST_OF = "bestOf";
   public static final String PROPERTY_PLAYERS = "players";
   private Boolean gameModeExtension;
   private int maxRounds;
   private int currentRound;
   private boolean bestOf;
   protected PropertyChangeSupport listeners;
   private List<Player> players;

   public Boolean getGameModeExtension()
   {
      return this.gameModeExtension;
   }

   public Game setGameModeExtension(Boolean value)
   {
      if (Objects.equals(value, this.gameModeExtension))
      {
         return this;
      }

      final Boolean oldValue = this.gameModeExtension;
      this.gameModeExtension = value;
      this.firePropertyChange(PROPERTY_GAME_MODE_EXTENSION, oldValue, value);
      return this;
   }

   public int getMaxRounds()
   {
      return this.maxRounds;
   }

   public Game setMaxRounds(int value)
   {
      if (value == this.maxRounds)
      {
         return this;
      }

      final int oldValue = this.maxRounds;
      this.maxRounds = value;
      this.firePropertyChange(PROPERTY_MAX_ROUNDS, oldValue, value);
      return this;
   }

   public int getCurrentRound()
   {
      return this.currentRound;
   }

   public Game setCurrentRound(int value)
   {
      if (value == this.currentRound)
      {
         return this;
      }

      final int oldValue = this.currentRound;
      this.currentRound = value;
      this.firePropertyChange(PROPERTY_CURRENT_ROUND, oldValue, value);
      return this;
   }

   public boolean getBestOf()
   {
      return this.bestOf;
   }

   public Game setBestOf(boolean value)
   {
      if (value == this.bestOf)
      {
         return this;
      }

      final boolean oldValue = this.bestOf;
      this.bestOf = value;
      this.firePropertyChange(PROPERTY_BEST_OF, oldValue, value);
      return this;
   }

   public List<Player> getPlayers()
   {
      return this.players != null ? Collections.unmodifiableList(this.players) : Collections.emptyList();
   }

   public Game withPlayers(Player value)
   {
      if (this.players == null)
      {
         this.players = new ArrayList<>();
      }
      if (!this.players.contains(value))
      {
         this.players.add(value);
         value.setGame(this);
         this.firePropertyChange(PROPERTY_PLAYERS, null, value);
      }
      return this;
   }

   public Game withPlayers(Player... value)
   {
      for (final Player item : value)
      {
         this.withPlayers(item);
      }
      return this;
   }

   public Game withPlayers(Collection<? extends Player> value)
   {
      for (final Player item : value)
      {
         this.withPlayers(item);
      }
      return this;
   }

   public Game withoutPlayers(Player value)
   {
      if (this.players != null && this.players.remove(value))
      {
         value.setGame(null);
         this.firePropertyChange(PROPERTY_PLAYERS, value, null);
      }
      return this;
   }

   public Game withoutPlayers(Player... value)
   {
      for (final Player item : value)
      {
         this.withoutPlayers(item);
      }
      return this;
   }

   public Game withoutPlayers(Collection<? extends Player> value)
   {
      for (final Player item : value)
      {
         this.withoutPlayers(item);
      }
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

   public void removeYou()
   {
      this.withoutPlayers(new ArrayList<>(this.getPlayers()));
   }
}
