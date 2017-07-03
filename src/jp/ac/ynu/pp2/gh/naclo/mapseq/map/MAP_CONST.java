package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

public class MAP_CONST {
	public static enum DIRECTION {
		RIGHT(0),
		UP(1),
		DOWN(3),
		LEFT(2);
		
		private int val;
		
		private DIRECTION(int a) {
			val = a;
		}
		
		public static DIRECTION getValue(int pA) {
			switch (pA) {
			case 0:
				return RIGHT;
			case 1:
				return UP;
			case 2:
				return DOWN;
			case 3:
				return LEFT;
			}
			throw new IndexOutOfBoundsException(""+pA);
		}
		
		public int getVal() {
			return val;
		}
	};
	
	public static enum STATE {
		EMPTY(0),
		BLOCK(1),
		NEXT(2);
		
		private int val;
		
		private STATE(int pA) {
			val = pA;
		}
		
		public static STATE getValue(int pA) {
			switch (pA) {
			case 0:
				return EMPTY;
			case 1:
				return BLOCK;
			case 2:
				return NEXT;
			}
			throw new IndexOutOfBoundsException(""+pA);
		}
		
		public int getVal() {
			return val;
		}
	}
	
	public static final int MAP_BOX_SIZE = 32;
	public static final int MAP_CHIP_SIZE = 16;
}
