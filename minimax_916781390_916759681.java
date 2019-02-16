import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class minimax_916781390_916759681 extends AIModule {
	ArrayList<Point> availMoves;
	private int player;

	public void getNextMove(final GameStateModule game) {
		int i = 1;
		availMoves = calculateMoves(game);
		//System.out.println(player +  "\n");
		while(!terminate) {
			//System.out.println("next turn \n");
				minimax(game,8);
				i++;
			}
	}

	private ArrayList<Point> calculateMoves(final GameStateModule game) {
		ArrayList<Point> temp = new ArrayList<Point>();
		temp.clear();
		for (int i = game.getWidth(); i >= 0; i--)
			if (game.canMakeMove(i)) {
				temp.add(new Point(i, game.getHeightAt(i)));
			}
		return  temp;
	}

	private void minimax(final GameStateModule game, int depth) {
		int highScore = Integer.MIN_VALUE;
		int bestCol = Integer.MIN_VALUE;
		ArrayList<Point> Arr = new ArrayList<Point>();
		Arr = calculateMoves(game);
		int temp = 0;

		for (int t = 0; t < Arr.size(); t++) {
			if(!terminate){
				game.makeMove((int) Arr.get(t).getX());
				temp = minVal(game, depth-1, Integer.MIN_VALUE, Integer.MAX_VALUE, Arr.get(t));
			if (temp >= highScore) {
				highScore = temp;
				bestCol = (int) Arr.get(t).getX();
			}
				game.unMakeMove();
			}	

		}
		if(bestCol < 0) {
			bestCol = chosenMove;
		}
		if(chosenMove==-1){
			chosenMove=bestCol;
		}

		//System.out.println(chosenMove+ " current best column" + bestCol);
		//game.makeMove(bestCol);
		if(game.canMakeMove(chosenMove) || game.canMakeMove(bestCol)){
			chosenMove = (terminate)?chosenMove:bestCol;
		}
		chosenMove = (terminate)?chosenMove:bestCol;
	}

	private int maxVal(final GameStateModule game, int depth, int alpha, int beta, Point p) {
		if (depth <= 0) {
			return evaluationFunction(game, depth, p);
		}
		int currentVal = Integer.MIN_VALUE;
		int high = Integer.MIN_VALUE;
		ArrayList<Point> Arr = new ArrayList<Point>();
		for(int i = 0; i < game.getWidth();i++) {
			if(game.canMakeMove(i)) {
				Arr.add(new Point(i,game.getHeightAt(i)));
			}
		}
		for (int t = 0; t < Arr.size(); t++) {
			if(!terminate){
			game.makeMove((int)  Arr.get(t).getX());
			currentVal = minVal(game, depth - 1, alpha, beta,  Arr.get(t));

			if (currentVal > high) {
				high = currentVal;
			}
			game.unMakeMove();

			if(alpha < high){
				alpha = high;
			}
			if(alpha >= beta){
				return high;
			}

		}
	}
		return high;
	}

	private int minVal(final GameStateModule game, int depth, int alpha, int beta, Point p) {
		if (depth <= 0 || game.isGameOver()) {
			return evaluationFunction(game, depth, p);
		}
		int currentVal = Integer.MAX_VALUE;;
		int low = Integer.MAX_VALUE;
		ArrayList<Point> Arr = new ArrayList<Point>();
		for(int i = 0; i < game.getWidth();i++) {
			if(game.canMakeMove(i)) {
				Arr.add(new Point(i,game.getHeightAt(i)));
			}
		}
		for (int t = 0; t < Arr.size(); t++) {
			if(!terminate){
			game.makeMove((int) Arr.get(t).getX());
			currentVal = maxVal(game, depth - 1, alpha, beta, Arr.get(t));

			if (currentVal < low) {
				low = currentVal;
			}
			game.unMakeMove();
			if(beta > low){
				beta = low;
			}
			if(beta <= alpha){
				return low;
			}

		}
	}

		return low;
	}

private int evaluationFunction(final GameStateModule game, int depth, Point p){

	int[] score = {0,0};
	int[] weight = {2,4,6,8,6,4,2};
 	if(game.isGameOver()) {
		return game.getWinner()==player?100:-100;
	}else {
		for (int player = 0; player <2 ; player ++){
			for (int i = -1; i <= 1 ; i++){
				for (int j = -1; j <=1; j++){
					if(checkAdjacent(game,p,i,j,player+1)){
						score[player]+=weight[p.x+i];
						if(checkAdjacent(game,p,2*i,2*j,player+1)){
							//score[player]+=weight[p.x+2*i];

						}
						if(checkAdjacent(game,p,(-1)*i,-(1)*j,player+1)){
						score[player]+=weight[p.x-i];
					}
					}




					}


				}
			}
		}

//	System.out.print("\nScore difference " + (score[0] - score[1]));

	if(player == 1) {
		return score[0]-score[1];
	}else {
		return score[1]-score[0];
	}



//	}
}
	private boolean checkAdjacent(final GameStateModule game,Point p, int i, int j,int player) {
		if(p.getX()+i < 0 || p.getX()+i > game.getWidth() || p.getY()+j < 0 || p.getY()+j> game.getHeight()){
			return false;
		} else {
			if(game.getAt(p.x+i,p.y+j) == player) {
				return true;
		}
		return false;

	}
}

}