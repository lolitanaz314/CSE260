package GameComponents;
import java.util.ArrayList;
import java.util.HashMap;

public class Bag {
	
	private ArrayList<Tile> bagList; // bag that starts out at 100 - keeps shrinking as players fill racks
	private ArrayList<Tile> letterPointList; // To score words with the getLetterScore(char) method in this class
	
	public Bag() {
		
		// assigning value and points
		Tile aTile = new Tile('A', 1);
        Tile bTile = new Tile('B', 3);
        Tile cTile = new Tile('C', 3);
        Tile dTile = new Tile('D', 2);
        Tile eTile = new Tile('E', 1);
        Tile fTile = new Tile('F', 4);
        Tile gTile = new Tile('G', 2);
        Tile hTile = new Tile('H', 4);
        Tile iTile = new Tile('I', 1);
        Tile jTile = new Tile('J', 8);
        Tile kTile = new Tile('K', 5);
        Tile lTile = new Tile('L', 1);
        Tile mTile = new Tile('M', 3);
        Tile nTile = new Tile('N', 1);
        Tile oTile = new Tile('O', 1);
        Tile pTile = new Tile('P', 3);
        Tile qTile = new Tile('Q', 10);
        Tile rTile = new Tile('R', 1);
        Tile sTile = new Tile('S', 1);
        Tile tTile = new Tile('T', 1);
        Tile uTile = new Tile('U', 1);
        Tile vTile = new Tile('V', 4);
        Tile wTile = new Tile('W', 4);
        Tile xTile = new Tile('X', 8);
        Tile yTile = new Tile('Y', 4);
        Tile zTile = new Tile('Z', 10);
        Tile blankTile = new Tile('?', 0);
        
        /*
        English Scrabble Letter points and distribution
        0 points: blank/wild (2)
        1 point: E (12), A (9), I (9), O (8), N (6), R (6), T (6), L (4), S (4), U (4)
        2 points: D (4), G (3)
        3 points: B (2), C (2), M (2), P (2)
        4 points: F (2), H (2), V (2), W (2), Y (2)
        5 points: K (1)
        8 points: J (1), X (1)
        10 points: Q (1), Z (1)
        */
        
        letterPointList = new ArrayList<>();
        letterPointList.add(aTile);
        letterPointList.add(bTile);
        letterPointList.add(cTile);
        letterPointList.add(dTile);
        letterPointList.add(eTile);
        letterPointList.add(fTile);
        letterPointList.add(gTile);
        letterPointList.add(hTile);
        letterPointList.add(iTile);
        letterPointList.add(jTile);
        letterPointList.add(kTile);
        letterPointList.add(lTile);
        letterPointList.add(mTile);
        letterPointList.add(nTile);
        letterPointList.add(oTile);
        letterPointList.add(pTile);
        letterPointList.add(qTile);
        letterPointList.add(rTile);
        letterPointList.add(sTile);
        letterPointList.add(tTile);
        letterPointList.add(uTile);
        letterPointList.add(vTile);
        letterPointList.add(wTile);
        letterPointList.add(xTile);
        letterPointList.add(yTile);
        letterPointList.add(zTile);
        letterPointList.add(blankTile);
        
        
        bagList = new ArrayList<>();
        
        for (int i = 0; i < 12; i++) {
            bagList.add(eTile);
        }

        //add 9 a and i tiles
        for (int i = 0; i < 9; i++) {
            bagList.add(aTile);
            bagList.add(iTile);
        }

        //add 8 o tiles
        for (int i = 0; i < 8; i++) {
            bagList.add(iTile);
        }

        //add 6 n,r and t tiles
        for (int i = 0; i < 6; i++) {
            bagList.add(nTile);
            bagList.add(rTile);
            bagList.add(tTile);
        }

        //add 4 l,s,u and d tiles
        for (int i = 0; i < 4; i++) {
            bagList.add(lTile);
            bagList.add(sTile);
            bagList.add(uTile);
            bagList.add(dTile);
        }

        //add 3 g tiles
        for (int i = 0; i < 3; i++) {
            bagList.add(gTile);
        }

        //add 2 b,c,m,p,f,h,v,w,y and blank tiles
        for (int i = 0; i < 2; i++) {
            bagList.add(bTile);
            bagList.add(cTile);
            bagList.add(mTile);
            bagList.add(pTile);
            bagList.add(fTile);
            bagList.add(hTile);
            bagList.add(vTile);
            bagList.add(wTile);
            bagList.add(yTile);
            bagList.add(blankTile);
        }

	        //add 1 k,j,x,q and z tiles
	        bagList.add(kTile);
	        bagList.add(jTile);
	        bagList.add(xTile);
	        bagList.add(qTile);
	        bagList.add(zTile);
    }

	
	// for the end of the game (end game when size == 0)
	public int size() {
		return bagList.size();
	}
	
	// do this to refill rack
	// randomly choose tile out of the bag and remove it 
	public Tile pullTile() {
		
		int index = (int) (Math.random() * (bagList.size() - 1));
		Tile pulledTile = bagList.remove(index);
		return pulledTile;
	}
	
	// Gets the score for each letter
	public int getLetterScore(char letter) {
		
		char upperCaseLetter = Character.toUpperCase(letter);
		int letterScore = 0;
		
		for (int i = 0; i < letterPointList.size(); i++) {
			if (upperCaseLetter == letterPointList.get(i).getLetter())
				letterScore = letterPointList.get(i).getLetterPoints();
		}
		
		return letterScore;
	}
	
} 
