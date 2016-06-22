package com.cloudcraftgaming.hideandseekplus.arena;

import com.cloudcraftgaming.hideandseekplus.game.GameState;
import com.cloudcraftgaming.hideandseekplus.game.WinType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Nova Fox on 6/14/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class Arena {
    private final int id;
    private final ArrayList<UUID> players = new ArrayList<>();
    private final ArrayList<UUID> spectators = new ArrayList<>();

    private UUID seeker;
    private int playerCount;
    private GameState gameState;
    private Scoreboard scoreboard;
    private boolean tag;

    private Boolean joinable;

    private WinType winType;

    private int waitId;
    private int startId;
    private int gameId;

    public Arena(int _id) {
        id = _id;
    }

    //Getters
    public int getId() {
        return id;
    }
    public ArrayList<UUID> getPlayers() {
        return players;
    }
    public ArrayList<UUID> getSpectators() {
        return spectators;
    }
    public UUID getSeeker() {
        return seeker;
    }
    public int getPlayerCount() {
        return playerCount;
    }
    public GameState getGameState() {
        return gameState;
    }
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
    public Boolean canTag() {
        return tag;
    }

    public Boolean isJoinable() {
        return joinable;
    }

    public WinType getWinType() {
        return winType;
    }

    public int getWaitId() {
        return waitId;
    }
    public int getStartId() {
        return startId;
    }
    public int getGameId() {
        return gameId;
    }

    //Setters
    public void setSeeker(UUID _seeker) {
        seeker = _seeker;
    }
    public void setPlayerCount(int _playerCount) {
        playerCount = _playerCount;
    }
    public void setGameState(GameState _gameState) {
        gameState = _gameState;
    }
    public void setScoreboard(Scoreboard _scoreboard) {
        scoreboard = _scoreboard;
    }
    public void setCanTag(Boolean _tag) {
        tag = _tag;
    }

    public void setJoinable(Boolean _joinable) {
        joinable = _joinable;
    }

    public void setWinType(WinType _winType) {
        winType = _winType;
    }

    public void setWaitId(int _waitId) {
        waitId = _waitId;
    }
    public void setStartId(int _startId) {
        startId = _startId;
    }
    public void setGameId(int _gameId) {
        gameId = _gameId;
    }
}