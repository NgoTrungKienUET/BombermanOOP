package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AIMedium extends AI {
	Bomber _bomber;
	Enemy _e;

	public AIMedium(Bomber bomber, Enemy e) {
		_bomber = bomber;
		_e = e;
	}

	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi
        if (_bomber == null) return 4;
        if (random.nextInt(2) == 0) {
            return setDirectionVertical();
        } else {
            return setDirectionHorizontal();
        }
	}
    private int setDirectionVertical() {
        int num = random.nextInt(2);
        if (_bomber.getYTile() < _e.getYTile()) {
            if (num == 0) return 0;
            else return 2;
        } else {
            if (num == 0) return 2;
            else return 0;
        }
    }

    private int setDirectionHorizontal() {
        int num = random.nextInt(2;
        if (_bomber.getXTile() < _e.getXTile()) {
            if (num == 0) return 1;
            else return 3;
        } else {
            if (num == 0) return 3;
            else return 1;
        }
    }

    private int decision(int randomNumber, int direction1, int direction2) {
        if (randomNumber == 0) {
            return direction1;
        } else {
            return direction2;
        }
    }
}
