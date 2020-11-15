package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Doria;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) {
		try {
			// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
			ClassLoader classLoader = this.getClass().getClassLoader();
			Scanner scanner = new Scanner(new File(classLoader.getResource("levels/Level"+level+".txt").getFile()));
			// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
			_level = scanner.nextInt();
			_height = scanner.nextInt();
			_width = scanner.nextInt();

			scanner.nextLine();
			_map = new char[_height][_width];

			for (int i = 0; i < _height; i++) {
				_map[i] = scanner.nextLine().substring(0, _width).toCharArray();
				System.out.println(_map[i]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		for (int x = 0; x < _width; x++) {
			for (int y = 0; y < _height; y++) {
				switch (_map[y][x]) {
					case '#':
						addWall(x, y);
						break;
					case 'p':
						addBomber(x, y);
						break;
					case '1':
						addBalloon(x, y);
						break;
					case '2':
						addOneal(x, y);
						break;
					case '3':
						addDoria(x, y);
						break;
					case '*':
						addBrick(x, y);
						break;
					case 'f':
						addFlameItem(x, y);
						break;
					case 'b':
						addBombItem(x, y);
						break;
					case 's':
						addSpeedItem(x, y);
						break;
					case 'x':
						addPortal(x, y);
						break;
					default:
						addGrass(x, y);
						break;
				}
			}
		}
	}

	private void addItem(int xI, int yI) {
		_board.addEntity(xI + yI * _width,
				new LayeredEntity(xI, yI,
						new Grass(xI ,yI, Sprite.grass),
						new SpeedItem(xI, yI, Sprite.powerup_flames),
						new Brick(xI, yI, Sprite.brick)
				)
		);
	}

	private void addGrass(int x, int y) {
		int pos = x + y * _width;
		_board.addEntity(pos, new Grass(x, y, Sprite.grass));
	}

	private void addWall(int x, int y) {
		int pos = x + y * _width;
		_board.addEntity(pos, new Wall(x, y, Sprite.wall));
	}

	private void addBomber(int xBomber, int yBomber) {
		_board.addCharacter( new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board) );
		Screen.setOffset(0, 0);
		_board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));
	}

	private void addBalloon(int xE, int yE) {
		_board.addCharacter( new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
		_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
	}

	private void addOneal(int xE, int yE) {
		_board.addCharacter(new Oneal(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
		_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
	}

	private void addDoria(int xE, int yE) {
		_board.addCharacter(new Doria(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
		_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
	}

	private void addBrick(int xB, int yB) {
		_board.addEntity(xB + yB * _width,
				new LayeredEntity(xB, yB,
						new Grass(xB, yB, Sprite.grass),
						new Brick(xB, yB, Sprite.brick)
				)
		);
	}
	private void addSpeedItem(int xI, int yI) {
		_board.addEntity(xI + yI * _width,
				new LayeredEntity(xI, yI,
						new Grass(xI ,yI, Sprite.grass),
						new SpeedItem(xI, yI, Sprite.powerup_speed),
						new Brick(xI, yI, Sprite.brick)
				)
		);
	}
	private void addPortal(int x, int y) {
		_board.addEntity(x + y * _width,
				new LayeredEntity(x, y,
						new Grass(x, y, Sprite.grass),
						new Portal(x, y, Sprite.portal, _board),
						new Brick(x,y,Sprite.brick)));
	}
	private void addBombItem(int xI, int yI) {
		_board.addEntity(xI + yI * _width,
				new LayeredEntity(xI, yI,
						new Grass(xI ,yI, Sprite.grass),
						new BombItem(xI, yI, Sprite.powerup_bombs),
						new Brick(xI, yI, Sprite.brick)
				)
		);
	}
	private void addFlameItem(int xI, int yI) {
		_board.addEntity(xI + yI * _width,
				new LayeredEntity(xI, yI,
						new Grass(xI ,yI, Sprite.grass),
						new FlameItem(xI, yI, Sprite.powerup_flames),
						new Brick(xI, yI, Sprite.brick)
				)
		);
	}
}
