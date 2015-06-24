

import java.io.IOException;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;

public class ELM {
	private static int []label;
	
	private static int numHiddenNeurons=25;
	private static int numOutputNeurons;//classes.
	private int numInputNeurons;//features.
	
	private static Matrix Wi;  //the input weight
	private static Matrix Bias; //the hidden nodes biases
	private static Matrix Beta; //the key of elm

	
	public ELM(int hiddenNode){
		numHiddenNeurons = hiddenNode;
		Bias = Matrix.factory.rand(1,numHiddenNeurons).times(2.0).minus(1.0);
		
	}
	
	protected void init(int features,int classes,int[] labels){
		numInputNeurons = features;
		numOutputNeurons = classes;
		label = labels;
		Wi = Matrix.factory.rand(numHiddenNeurons,numInputNeurons).times(2.0).minus(1.0);
	}
	
	public static Matrix labelExtened(Matrix labelVector){             //这个函数是对输入的所有样例进行重打标签
		Matrix reT = Matrix.factory.zeros(labelVector.getRowCount(),numOutputNeurons);
		for(long i=0;i<labelVector.getRowCount();i++){
			for(int j=0;j<numOutputNeurons;j++){
				if(label[j]==labelVector.getAsInt(i,0))
					reT.setAsInt(1, i,j);
				else
					reT.setAsInt(-1, i,j);
			}
		}
		return reT;
	}
	
	//sigmoidal function for calculating H Matrix.
		public static Matrix SigActFun(Matrix P,Matrix Wi,Matrix bias){
			Matrix XWibi = P.mtimes(Wi.transpose());//N*l
			Matrix biasMatrix = Matrix.factory.zeros(P.getRowCount(),numHiddenNeurons);
			for(long i = 0;i<P.getRowCount();i++){
				for(long j = 0;j<numHiddenNeurons;j++)
					biasMatrix.setAsDouble(bias.getAsDouble(0,j), i,j);
			}
			XWibi = XWibi.plus(biasMatrix);
			Matrix H = XWibi.mtimes(-1.0);
			H = H.exp(Ret.NEW);
			H = H.plus(1.0);
			Matrix ones = Matrix.factory.ones(H.getSize());
			H = ones.divide(H);		
			return H;//N*l
		}
		
	//return T vector of test file. 返回标签
		public static Matrix GetMaxColumn(Matrix T){
			Matrix vector = Matrix.factory.zeros(T.getRowCount(),1);
			for(long i = 0;i<T.getRowCount();i++){
				long maxColumn = 0;
				double maxValue = T.getAsDouble(i,0);
				for(long j = 1;j<T.getColumnCount();j++){
					if(maxValue < T.getAsDouble(i,j))
					{
						maxColumn = j;
						maxValue = T.getAsDouble(i,j);
					}
				}
				vector.setAsLong(maxColumn, i,0);
			}
			return vector;
		}
		
	//Calculate Accuracy Method,count the different number between the two vectors return.
		public static double CalAcc(Matrix Ttarget,Matrix Tactual){
			long miss = 0;
			if(Ttarget.getRowCount()!=Tactual.getRowCount())
				return 0;
			long total = Ttarget.getRowCount();
			Matrix vecTtarget = GetMaxColumn(Ttarget);
			Matrix vecTactual = GetMaxColumn(Tactual);
//			System.out.println(vecTtarget);
//			System.out.println(vecTactual);
			for(long i = 0;i<total;i++)
			{
				if(vecTtarget.getAsLong(i,0)!=vecTactual.getAsLong(i,0))
					miss++;
			}
			
			return 1.0 - (1.0 * miss / total);
		}
	
	public void train(String TrainFile,int classes,int[] labels) throws MatrixException, IOException{
		Matrix tempImport = MatrixFactory.importFromFile(FileFormat.CSV, TrainFile," ");
		this.init((int)tempImport.getColumnCount()-1, classes,labels);
		Matrix Tvector = tempImport.subMatrix(Ret.NEW, 0, 0, tempImport.getRowCount()-1, 0);
		//get the matrix of T and P in training dataset
		Matrix TrainTExtened = labelExtened(Tvector);
		Matrix TrainP = tempImport.subMatrix(Ret.NEW, 0, 1, tempImport.getRowCount()-1, tempImport.getColumnCount()-1);
		Matrix H = SigActFun(TrainP,Wi,Bias);
		Beta = H.pinv().mtimes(TrainTExtened);   //pinv()是求逆矩阵函数

	}
	
	public void testTrain(String testfile) throws MatrixException, IOException{
		Matrix testMatrix = MatrixFactory.importFromFile(FileFormat.CSV, testfile," ");
		Matrix Ttarget = testMatrix.subMatrix(Ret.NEW, 0, 0, testMatrix.getRowCount()-1, 0); //选取子矩阵
		Ttarget = labelExtened(Ttarget);
		long start = System.nanoTime();
		Matrix P = testMatrix.subMatrix(Ret.NEW, 0, 1, testMatrix.getRowCount()-1, testMatrix.getColumnCount()-1);//属性矩阵
		Matrix H = SigActFun(P,Wi,Bias);
		Matrix Tactual = H.mtimes(Beta);
		Matrix targetLabel = GetMaxColumn(Ttarget);
		Matrix actualLabel = GetMaxColumn(Tactual);

		double acc = CalAcc(Ttarget,Tactual);   
		System.out.println("The Acc: "+acc);
		
		System.out.println("Calculate Accuracy took: " +(double)(System.nanoTime()-start)/1000000000 + "s");
	}
	
	public void testPredict(String testfile) throws MatrixException, IOException{
		Matrix testMatrix = MatrixFactory.importFromFile(FileFormat.CSV, testfile," ");
		Matrix Ttarget = testMatrix.subMatrix(Ret.NEW, 0, 0, testMatrix.getRowCount()-1, 0);
		Ttarget = labelExtened(Ttarget);
//		System.out.println(Ttarget);
		long start = System.nanoTime();
		Matrix P = testMatrix.subMatrix(Ret.NEW, 0, 1, testMatrix.getRowCount()-1, testMatrix.getColumnCount()-1);
		Matrix H = SigActFun(P,Wi,Bias);
		Matrix Tactual = H.mtimes(Beta);
		Matrix targetLabel = GetMaxColumn(Ttarget);
		Matrix actualLabel = GetMaxColumn(Tactual);
		
		System.out.println("真实标签：\n"+targetLabel);
		System.out.println("预测标签：\n"+actualLabel);

		double acc = CalAcc(Ttarget,Tactual);   
		System.out.println("The Acc: "+acc);
		
//		System.out.println(Ttarget);
		System.out.println("Calculate Accuracy took: " +(double)(System.nanoTime()-start)/1000000000 + "s");
	}
	public Matrix actualT(String testfile) throws MatrixException, IOException{
		Matrix testMatrix = MatrixFactory.importFromFile(FileFormat.CSV, testfile," ");
		Matrix P = testMatrix.subMatrix(Ret.NEW, 0, 1, testMatrix.getRowCount()-1, testMatrix.getColumnCount()-1);
		Matrix H = SigActFun(P,Wi,Bias);
		return H.mtimes(Beta);
	}
	
	
	
	public static double[] findStepBestNum(int step,int lower,int upper,String trainfile,String testfile)throws MatrixException, IOException{
//		int step=15,bestNum=1,pre;
		int bestNum=1,pre;
		double bestAcc = 0;
		boolean flag = false,isFirst = false;      //flag用来判断是否出现过极值,isFirst用来判断是否是第一个小步长的极值
		int[] labels=new int[585];
		labels[0] = -1;
		for(int i=1;i<585;i++)
		{
			labels[i]  = i;
		}
		while(lower<=upper){
			pre = lower;
			ELM elm = new ELM(lower);
			
			elm.train(trainfile, labels.length, labels);
			elm.testPredict(testfile);
			Matrix testMatrix = MatrixFactory.importFromFile(FileFormat.CSV, testfile," ");
			Matrix Ttarget = testMatrix.subMatrix(Ret.NEW, 0, 0, testMatrix.getRowCount()-1, 0); //选取子矩阵
			Ttarget = labelExtened(Ttarget);
			Matrix P = testMatrix.subMatrix(Ret.NEW, 0, 1, testMatrix.getRowCount()-1, testMatrix.getColumnCount()-1);//属性矩阵
			Matrix H = SigActFun(P,Wi,Bias);
			Matrix Tactual = H.mtimes(Beta);
			double acc = CalAcc(Ttarget,Tactual); 
			if(bestAcc < acc ){
				bestAcc = acc;
				bestNum = lower;
				System.out.println("This turn's nodes num is:"+lower+"\n");
			}
			else{
				System.out.println("This turn's nodes num is:"+lower+"\n");
				if(isFirst){
					if(bestAcc < acc)bestNum = lower;
					break;
				}
				upper = lower+step;
				lower = pre-step;
				step = 1;
				flag = true;
				isFirst = true;
	            
			}
				lower = lower+step;
				
		}
		if(flag = false){
			System.out.println("The upper limit may be small!");
		}
		 System.out.println("the best hidden nodes numbers is:"+bestNum);
		 double[] best = {bestNum,bestAcc};
		return best;	
	}
	
	public static double findBestNum(int lower,int upper,String trainfile,String testfile)throws MatrixException, IOException{
		int step;
		double curBestAcc,curBestNum,BestAcc=0,BestNum=0,cur[];
		for(int i=5;i<=30;i++){
			cur = findStepBestNum(i,lower,upper,trainfile,testfile);
			curBestAcc = cur[1];
			curBestNum = cur[0];
			if(curBestAcc >= BestAcc){
				BestAcc = curBestAcc;
				BestNum = curBestNum;
			}
					
		}
		System.out.println("**********"+BestNum);
		return BestNum;
	}

	/*
	public static void main(String[] args) throws Exception{
        double bestNum;
		String trainfile = "upperround_oneway_final.txt";
	    String testfile = "upperchosed1_test.txt";
//	    FindBestHiddenLayerNodes best = new FindBestHiddenLayerNodes();
//	    bestNum = best.findBestNum(20,1000,trainfile,testfile);
	    bestNum = findBestNum(20,1000,trainfile,testfile);
	   
	    ELM elm = new ELM((int)(bestNum))  ;
		int[] lables=new int[585];
		lables[0] = -1;
		for(int i=1;i<585;i++)
		{
			lables[i]  = i;
		}
		
	    long start = System.nanoTime();
		elm.train(trainfile,lables.length,lables);
		System.out.println("Training time took: " +(double)(System.nanoTime()-start)/1000000000 + "s");
		System.out.println("Training Acc:");
		elm.testTrain(trainfile);
		System.out.println("Test Acc:");
		elm.testPredict(testfile);
		
	}	
	
}
*/
	
	public static void main(String[] args) throws Exception{

		ELM elm = new ELM(690);
		int[] lables=new int[585];
		lables[0] = -1;
		for(int i=1;i<585;i++)
		{
			lables[i]  = i;
		}
		
		  String trainfile = "./afterpaper/nor_interpolation5.txt";
		  String testfile = "./afterpaper/upperFilter010407.txt";
		    
//		String trainfile = "Mfilter368910.txt";
//	    String testfile = "filter010407.txt";
		
//	    String trainfile = "./afterpaper/upperMfilter368910.txt";
//	    String testfile = "./afterpaper/upperFilter010407.txt";
//	    
//		String trainfile = "upperangleInterpolation.txt";
//	    String testfile = "upperchosed1_test.txt";
	    long start = System.nanoTime();
		elm.train(trainfile,lables.length,lables);
		System.out.println("Training time took: " +(double)(System.nanoTime()-start)/1000000000 + "s");
		System.out.println("Training Acc:");
		elm.testTrain(trainfile);
		System.out.println("Test Acc:");
		elm.testPredict(testfile);
		
	}	
	
}























