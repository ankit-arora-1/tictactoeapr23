package models;

import exceptions.BotCountMoreThanOneException;
import exceptions.DuplicateSymbolException;
import exceptions.PlayerCountMismatchException;
import strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private Player winner;
    private GameState gameState;
    private int nextMovePlayerIndex;
    private List<WinningStrategy> winningStrategies;

    private Game(List<Player> players,
                int dimensions,
                List<WinningStrategy> winningStrategies) {
        this.players = players;
        this.winningStrategies = winningStrategies;
        this.board = new Board(dimensions);
        this.moves = new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
    }

    static class Builder {
        private List<Player> players;
        private int size;
        private List<WinningStrategy> winningStrategies;

        private Builder() {
            this.players = new ArrayList<>();
            this.winningStrategies = new ArrayList<>();
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder addPlayer(Player player) {
            this.players.add(player);
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public Builder addWinningStrategy(WinningStrategy winningStrategy) {
            this.winningStrategies.add(winningStrategy);
            return this;
        }

        // TODO: Move the validation logic to another class
        public void validatePlayersCount() throws PlayerCountMismatchException {
            if(players.size() != size - 1) {
                throw new PlayerCountMismatchException();
            }
        }

        public void validateUniqueSymbolForPlayer() throws DuplicateSymbolException {
            Map<Character, Integer> count = new HashMap<>();
            for(Player player: players) {
                Character symbol = player.getSymbol().getaChar();

                count.put(symbol, count.getOrDefault(symbol, 0) + 1);
                if(count.get(symbol) > 1) {
                    throw new DuplicateSymbolException();
                }
            }
        }

        public void validateBotCount() throws BotCountMoreThanOneException {
            int botCount = 0;
            for(Player player: players) {
                if(player.getPlayerType() == PlayerType.BOT) {
                    botCount += 1;
                }
            }

            if(botCount > 1) {
                 throw new BotCountMoreThanOneException();
            }
        }

        // TODO: Add more exceptions to check nulls, etc

        public void validate() throws BotCountMoreThanOneException, PlayerCountMismatchException, DuplicateSymbolException {
            validateBotCount();
            validatePlayersCount();
            validateUniqueSymbolForPlayer();
        }

        public Game build() throws BotCountMoreThanOneException, DuplicateSymbolException, PlayerCountMismatchException {
            validate();
            return new Game(players, size, winningStrategies);
        }
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextMovePlayerIndex() {
        return nextMovePlayerIndex;
    }

    public void setNextMovePlayerIndex(int nextMovePlayerIndex) {
        this.nextMovePlayerIndex = nextMovePlayerIndex;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }
}
