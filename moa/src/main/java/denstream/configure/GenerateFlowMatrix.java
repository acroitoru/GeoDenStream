package denstream.configure;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class GenerateFlowMatrix 
{
	/////////////////////////////////////////////////////////////////////////////////
	//
	//
	//
	/////////////////////////////////////////////////////////////////////////////////
	public static void genIntervalFlowMatrix(String outfileName_IntervalFlowMatrix,
			int iInterval, int max_cluster_id,
			int interval_record_count,
			HashMap<Integer, Integer> cluster_id_hash,
			HashMap<Integer, Integer> connected_cluster_id_hash)
	{
		Integer interval_idx = iInterval+1;
        String outfileName_cur_day = outfileName_IntervalFlowMatrix + "flowprob_matrix"+ interval_idx.toString()+".csv";
        
        double[][] matrix_data = new double[max_cluster_id][max_cluster_id];
        for (int iRow = 0; iRow < max_cluster_id; iRow++)
        {
            for (int iCol = 0; iCol < max_cluster_id; iCol++)
            {
            	matrix_data[iRow][iCol] = 0;                
            }
        }
        
        Integer all_link_count = 0;
        for (int iRecord = 0; iRecord < interval_record_count; iRecord++)
        {
			Integer cluster_id_value = cluster_id_hash.get(iRecord);
			if (cluster_id_value != null)
			{
				Integer recluster_id_value = connected_cluster_id_hash.get(iRecord);
				
				matrix_data[cluster_id_value - 1][recluster_id_value - 1] = 
						matrix_data[cluster_id_value - 1][recluster_id_value - 1] + 1;
				all_link_count++;
			}
        }

        for (int iRow = 0; iRow < max_cluster_id; iRow++)
        {
            for (int iCol = 0; iCol < max_cluster_id; iCol++)
            {
            	matrix_data[iRow][iCol] = matrix_data[iRow][iCol];
            }
        }
        
        try {
			BufferedWriter outBufWriter =new BufferedWriter(new FileWriter(outfileName_cur_day));
			
			outBufWriter.write(all_link_count.toString());
            for (int i = 0; i < max_cluster_id; i++)
            {
            	Integer idx = i+1;
            	outBufWriter.write(",");
            	outBufWriter.write(idx.toString());                
            }
            outBufWriter.write("\n");
            
            for (int iRow = 0; iRow < max_cluster_id; iRow++)
            {
            	Integer idx = iRow+1;
            	outBufWriter.write(idx.toString());
                for (int iCol = 0; iCol < max_cluster_id; iCol++)
                {
                	Double val = matrix_data[iRow][iCol];
                	outBufWriter.write(",");
                	outBufWriter.write(val.toString());
                }
                outBufWriter.write("\n");
            }
			outBufWriter.flush();
			outBufWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}