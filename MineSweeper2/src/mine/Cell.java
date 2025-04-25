package mine;

public class Cell {

	private CellState state;
	private int property; // 주변 지뢰 수

	public Cell() {
		state = CellState.Covered; 
		property = 0; 
	}

	public CellState getState() { 
		return state;
	}

	public void setState(CellState state) {
		this.state = state;
	}

	public int getProperty() { //지뢰수 반환
		return property;
	}

	public void setProperty(int property) { //지뢰수 세팅
		this.property = property;
	}

}
