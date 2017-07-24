package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.awt.Color;
import java.awt.Point;
import java.util.Vector;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

public class MeiroObject extends MapProgObject{
	public MeiroObject(MapHandlerBase pHandler, int bx, int by,String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);
		setOperator(new MeiroOperator());

		for(int i = 0 ;i < 52; i++) {
			for(int j = 0; j < 48; j++) {
				getMap().setObj(bx + j, by + i, this);
			}
		}
	}
	private Vector<Point> result = new Vector<Point>();
	private int showMap[][];

	public class MeiroOperator{
		private int map[][] = {{1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,0,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,0,0,1},
				{1,0,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1,0,1,1},
				{1,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,1},
				{1,0,0,0,1,0,0,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1,0,1},
				{1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,0,1,0,0,0,0,1,0,1},
				{1,0,0,0,1,0,0,0,0,1,1,0,1,1,1,0,1,1,1,1,1,1,0,1},
				{1,0,1,0,0,0,1,1,0,0,0,0,1,1,1,0,1,0,0,0,1,0,0,1},
				{1,0,1,1,1,0,0,0,1,1,1,1,1,1,0,0,1,0,1,0,1,1,0,1},
				{1,0,1,1,0,1,1,0,0,0,0,1,0,0,0,1,1,0,1,1,1,0,0,1},
				{1,0,1,0,0,0,0,1,1,1,0,1,0,1,0,1,0,0,1,0,0,0,1,1},
				{1,0,1,0,1,1,0,0,0,1,0,1,0,1,0,1,0,1,0,0,1,0,1,1},
				{1,0,0,0,1,1,1,1,0,1,0,0,0,1,0,1,0,1,1,1,1,0,1,1},
				{1,1,1,1,1,0,0,0,0,1,1,1,1,1,0,0,0,1,0,0,0,0,0,1},
				{1,0,0,0,0,0,1,1,0,0,0,0,1,0,1,0,1,1,0,1,1,1,1,1},
				{1,0,1,1,1,1,0,1,1,1,1,0,1,0,1,0,1,1,0,0,0,0,0,1},
				{1,0,1,0,0,1,0,0,1,0,0,0,1,0,1,0,1,1,1,1,1,1,0,1},
				{1,0,1,1,0,1,1,0,1,0,1,1,1,0,1,0,0,1,0,0,0,1,0,1},
				{1,0,0,0,0,0,1,0,1,0,0,0,0,0,1,1,0,0,0,1,0,1,0,1},
				{1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1},
				{1,1,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,1,1,1,1,1,1,1},
				{1,0,0,0,1,0,1,1,1,1,1,1,0,0,0,1,0,0,0,1,0,0,0,1},
				{1,0,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1},
				{1,0,1,1,1,1,0,1,0,1,0,0,0,1,0,0,0,1,0,0,0,1,1,1},
				{1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1}
				};
		private int returnMap[][];		//mapが書き換えられないため
		private int start[] = {22, 25};
		private int returnStart[];
		private int goal[] = {1, 0};
		private int returnGoal[];
		private boolean failFlag = false;


		public int[] getStartPos(){
			returnStart = new int[2];
			returnStart[0] = start[0];
			returnStart[1] = start[1];
			return returnStart;
		}

		public int[] getGoalPos(){
			returnGoal = new int[2];
			returnGoal[0] = goal[0];
			returnGoal[1] = goal[1];
			return returnGoal;
		}

		public int[][] getMap(){
			returnMap = new int[map.length][map[0].length];
			for(int i = 0; i < map.length; i++){
				for(int j = 0; j < map[i].length; j++){
					returnMap[i][j] = map[i][j];
				}
			}
			return returnMap;
		}

		public void setResult(int x, int y){
			if(failFlag){
				return;
			}
			if(x < 0 || x >= map[0].length || y < 0 || y >= map.length || map[y][x] != 0){
				failFlag = true;
				return;
			}
			result.add(new Point(x, y));
		}
	}

	@Override
	public String getMethodName(){
		return "solve";
	}
	@Override
	public String getArgumentString() {
		return "labyrinth";
	}
	int animeCount = 0;
	int delay = 50;
	int length = 10;
	int tail = -10;
	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		sinfo.g.fillRect(map_x,map_y,
				MAP_CONST.MAP_BOX_SIZE *  48, MAP_CONST.MAP_BOX_SIZE * 52);
		for(int i = tail; i < result.size() && (i - tail) < length; i++){
			if(i < 0) continue;
			sinfo.g.setColor(Color.yellow);
			sinfo.g.fillRect(map_x + (int)result.get(i).getX() * MAP_CONST.MAP_BOX_SIZE * 2,
					map_y + (int)result.get(i).getY() * MAP_CONST.MAP_BOX_SIZE * 2,
					MAP_CONST.MAP_BOX_SIZE * 2, MAP_CONST.MAP_BOX_SIZE * 2);
		}
		if(animeCount % delay == 0) {
			tail++;
			if(tail >= result.size()) {
				tail = -10;
			}
		}

		animeCount++;
		drawFlag = true;
	}

	@Override
	public void update(ShareInfo sinfo) {
		drawFlag = false;
	}

	@Override
	public boolean hitCheck(MapObject obj) {
		return false;
	}
}
/*
def solve(labyrinth)
  Array map = labyrinth.getMap()
  Array start = labyrinth.getStartPos()
  Array goal = labyrinth.getGoalPos()
  map[start[1]][start[0]] = 2           #スタート地点
  Array heap = []
  Array element = Array.new(3)
  element[0] = start[0]
  element[1] = start[1]
  element[2] = distance(goal, start[0], start[1])
  push(heap, element)
  if solveSub(heap, map, goal) then
    Integer x = goal[0]
    Integer y = goal[1]
    Array result = []
    while distance(start, x, y) != 0
      result << [x, y]
      if map[y][x] == 3 then
        x = x - 1
      elsif map[y][x] == 4 then
        x = x + 1
      elsif map[y][x] == 5 then
        y = y - 1
      elsif map[y][x] == 6 then
        y = y + 1
      end
    end
    result << [x, y]
    Integer i = result.length() - 1
    while i >= 0
      labyrinth.setResult(result[i][0], result[i][1])
      i = i - 1
    end
  end
end

def solveSub(heap, map, goal)
    Array direct = [[1, 0], [-1, 0], [0, 1], [0, -1]]
    Array directC = [3, 4, 5, 6]  #'L', 'R', 'U', 'B'
    Integer x = 0
    Integer y = 0
    Integer i = 0
    while heap.length != 0
      element = pop(heap)
      #p element
      i = 0
      while i  < direct.length
        x = element[0] + direct[i][0]
        y = element[1] + direct[i][1]
        #p x, y
        if x < 0 || x >= map[0].length || y < 0 || y >= map.length then
          i = i + 1
          next
        end
        if map[y][x] != 0 then
          i = i + 1
          next
        end
        map[y][x] = directC[i]
        Array newElement = Array.new(3)
        newElement[0] = x
        newElement[1] = y
        newElement[2] = distance(goal, x, y)
        if newElement[2] == 0 then
          return true
        end
        push(heap, newElement)
        i = i + 1
      end
      #p heap
    end
    return false
end

def distance(pos , x, y)
  return ((pos[0] - x) * (pos[0] - x)  + (pos[1] - y) * (pos[1] - y))
end

def push(heap, values)
  heap << values
  Integer pos = heap.length - 1

  while pos > 0
    if(heap[(pos - 1) / 2][2] <= values[2]) then
      break
    end
    Array t = heap[(pos - 1) / 2]
    heap[(pos - 1) / 2] = heap[pos]
    heap[pos] = t
    pos = (pos - 1) / 2
  end
end

def pop(heap)
  Array result = heap[0]
  heap[0] = heap[heap.length - 1]
  heap.delete_at(heap.length - 1)

  Integer pos = 0
  Integer left = 10000 #十分に大きい数字
  Integer right = 10000

  while pos < heap.length
    if pos * 2 + 1 < heap.length then
      left = heap[pos * 2 + 1][2]
    else
      left = 10000
    end
    if pos * 2 + 2 < heap.length then
      right = heap[pos * 2 + 2][2]
    else
      right = 10000
    end
    Array t = []
    if heap[pos][2] < left && heap[pos][2] < right then
      break
    end

    if left < right then
      t = heap[pos]
      heap[pos] = heap[pos * 2 + 1]
      heap[pos * 2 + 1] = t
      pos = pos * 2 + 1
    else
      t = heap[pos]
      heap[pos] = heap[pos * 2 + 2]
      heap[pos * 2 + 2] = t
      pos = pos * 2 + 2
    end
  end
  return result
end
*/
