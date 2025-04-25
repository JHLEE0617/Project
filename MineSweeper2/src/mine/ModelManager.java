package mine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
 
import Presenter.Core;
 
public class  ModelManager {
	private Cell[][] cells;
	private int mineCount = 40;
	private Set<int[]> revealedMines = new HashSet<>();

	public static int CellXSize = 30;
	public static int CellYSize = 16;

	public ModelManager() {
		cells = new Cell[CellYSize][CellXSize];
	}
    
    public void initNewGame() {
        initCells();
        
        initMine();
        
        initProperties();
        
    }
    
    private void initCells() { // 셀 생성
        for(int y=0; y<CellYSize; y++) {
            for(int x=0; x<CellXSize; x++) {
                cells[y][x] = new Cell();
            }
        }
    }
    
    
    private void initMine() { //지뢰 설정코드, 중복제외 좌표에 랜덤으로 지뢰 세팅
        Random random = new Random();
        Set<Integer>set = new HashSet<Integer>();
        
        while(set.size() != mineCount){
            set.add(random.nextInt(CellXSize*CellYSize));
        }
        
        for(int mm : set) {
            int x,y;
            x = mm%CellXSize;
            y = (mm/CellXSize);
            
            cells[y][x].setProperty(Core.Mined);
        }
    }
 
    private void initProperties() {
        
        for(int y=0; y<CellYSize; y++) {
            for(int x=0; x<CellXSize; x++) {
                if(cells[y][x].getProperty()!=Core.Mined) 
                    cells[y][x].setProperty(getMineCountAroundCell(x, y));
            }
        }
        
    }
 
    private int getMineCountAroundCell(int x, int y) { // 주위 3x3 지뢰수 카운트
        int mineCountAroundCell = 0;
        
        if(x>0 && x<CellXSize-1 && y>0 && y<CellYSize-1) {
            if(cells[y][x+1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y][x-1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y+1][x-1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y+1][x].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y+1][x+1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y-1][x-1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y-1][x].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y-1][x+1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            
            
        }else if(x==0 && y==0) {
            if(cells[y][x+1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y+1][x].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y+1][x+1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            
        
        }else if(x==(CellXSize-1) && y==0) {
            if(cells[y][x-1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y+1][x].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y+1][x-1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            
        }else if(x==0 && y==(CellYSize-1)) {
            if(cells[y][x+1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y-1][x].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y-1][x+1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            
        }else if(x==(CellXSize-1) && y==(CellYSize-1)) {
            if(cells[y-1][x-1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y-1][x].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y][x-1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            
        }else if(x==0) {
            if(cells[y][x+1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y-1][x+1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y+1][x+1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y-1][x].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y+1][x].getProperty()==Core.Mined)
                mineCountAroundCell++;
            
        }else if(x==CellXSize-1) {
            if(cells[y][x-1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y-1][x-1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y+1][x-1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y-1][x].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y+1][x].getProperty()==Core.Mined)
                mineCountAroundCell++;
            
        }else if(y==0) {
            if(cells[y+1][x+1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y+1][x].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y+1][x-1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y][x-1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y][x+1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            
        }else if(y==CellYSize-1) {
            if(cells[y-1][x-1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y-1][x].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y-1][x+1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y][x-1].getProperty()==Core.Mined)
                mineCountAroundCell++;
            if(cells[y][x+1].getProperty()==Core.Mined)
                mineCountAroundCell++;
        }
        return mineCountAroundCell;
    }
    // 아래는 셀 하나씩에 대한 반환, 세팅 함수
    public CellState getCellState(int x, int y) {
        
        return cells[y][x].getState();
    }
 
    public int getCellProperty(int x, int y) {
        
        return cells[y][x].getProperty();
    }
 
    public void setCellState(int x, int y, CellState cellState) {
        cells[y][x].setState(cellState);
    }
 
    public int getMineCount() {
        return mineCount;
    }
    public void setMineCount(int i) {
		this.mineCount = i;
	}
   
 
    public void NewGame() { // 초기화
        resetCells();
        initMine();
        initProperties();
    }
 
    private void resetCells() {
        for(int y=0; y<CellYSize; y++) {
            for(int x=0; x<CellXSize; x++) {
                cells[y][x].setState(CellState.Covered);
                cells[y][x].setProperty(0);
            }
        }
    }

    public void decreaseMineCount() {
        if (mineCount > 0) {
            mineCount--;
        }
    }

    public void increaseMineCount() {
        mineCount++;
    }
    
    public int[] getRandomMineLocation() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(CellXSize);
            y = random.nextInt(CellYSize);
        } while (cells[y][x].getProperty() != Core.Mined);
        return new int[]{x, y};
    }
    
    public int[] getRandomUnrevealedMineLocation() {
        List<int[]> unrevealedMines = new ArrayList<>();
        for (int y = 0; y < CellYSize; y++) {
            for (int x = 0; x < CellXSize; x++) {
                if (cells[y][x].getProperty() == Core.Mined && !revealedMines.contains(new int[]{x, y})) {
                    unrevealedMines.add(new int[]{x, y});
                }
            }
        }
        if (unrevealedMines.isEmpty()) {
            return null;
        }
        int[] selectedMine = unrevealedMines.get(new Random().nextInt(unrevealedMines.size()));
        revealedMines.add(selectedMine);
        return selectedMine;
    }

    public void resetRevealedMines() {
        revealedMines.clear();
    }
    
    public void revealMine(int x, int y) {
        if (x >= 0 && x < CellXSize && y >= 0 && y < CellYSize) {
            Cell cell = cells[y][x];
            if (cell.getProperty() == Core.Mined) {
                cell.setState(CellState.Opened);
                cell.setProperty(0);
            }
        }
    }
}