import java.io.IOException;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;


public class FindBestHiddenLayerNodes {
	private int []label;
	private int numHiddenNeurons=25;
	private int numOutputNeurons;//classes.
	private int numInputNeurons;//features.
	private Matrix Wi;
	private Matrix Bias;
	private Matrix Beta;
	
	public Matrix labelExtened(Matrix labelVector){             //这个函数是对输入的所有样例进行重打标签
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
	public Matrix SigActFun(Matrix P,Matrix Wi,Matrix bias){
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
		
	public Matrix GetMaxColumn(Matrix T){
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
	public double CalAcc(Matrix Ttarget,Matrix Tactual){
		long miss = 0;
		if(Ttarget.getRowCount()!=Tactual.getRowCount())
			return 0;
		long total = Ttarget.getRowCount();
		Matrix vecTtarget = GetMaxColumn(Ttarget);
		Matrix vecTactual = GetMaxColumn(Tactual);
//				System.out.println(vecTtarget);
//				System.out.println(vecTactual);
		for(long i = 0;i<total;i++)
		{
			if(vecTtarget.getAsLong(i,0)!=vecTactual.getAsLong(i,0))
				miss++;
		}
		
		return 1.0 - (1.0 * miss / total);
	}
	public int findBestNum(int lower,int upper,String trainfile,String testfile)throws MatrixException, IOException{
		int step=10,bestNum=1,pre;
		double bestAcc = 0;
		boolean flag = false,isFirst = false;      //flag用来判断是否出现过极值,isFirst用来判断是否是第一个小步长的极值
		int[] labels=new int[585];
		labels[0] = -1;
		for(int i=1;i<585;i++)
		{
			labels[i]  = i;
		}
		while(lower<=upper){
//			step = (int) Math.ceil((lower+upper)/2);
			ELM elm = new ELM(lower);
			pre = lower;
			elm.train(trainfile, labels.length, labels);
			elm.testPredict(testfile);
			Matrix testMatrix = MatrixFactory.importFromFile(FileFormat.CSV, testfile," ");
			Matrix Ttarget = testMatrix.subMatrix(Ret.NEW, 0, 0, testMatrix.getRowCount()-1, 0); //选取子矩阵
			Ttarget = labelExtened(Ttarget);
			Matrix P = testMatrix.subMatrix(Ret.NEW, 0, 1, testMatrix.getRowCount()-1, testMatrix.getColumnCount()-1);//属性矩阵
			Matrix H = SigActFun(P,Wi,Bias);
			Matrix Tactual = H.mtimes(Beta);
			double acc = CalAcc(Ttarget,Tactual); 
			if(bestAcc <= acc ){
				bestAcc = acc;
				bestNum = lower;
			}
			else{
				if(isFirst){
					bestNum = lower;
					break;
				}
				upper = lower;
				lower = pre;
				step = 1;
				flag = true;
				isFirst = true;
	            
			}
			
				
			lower = lower+step;
			
		}
		if(flag = false){
			System.out.println("The upper limit may be small!");
		}
		 
		return bestNum;
		
	}

}
