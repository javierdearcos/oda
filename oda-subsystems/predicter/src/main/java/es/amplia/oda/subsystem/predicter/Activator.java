package es.amplia.oda.subsystem.predicter;

import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.CpuBackend;
import org.nd4j.linalg.factory.Nd4j;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Thread.currentThread().setContextClassLoader(CpuBackend.class.getClassLoader());

		// load the model
		String simpleMlp = "/home/debian/model.h5";
		double[] data = {10,115,0,0,0,35.3,0.134,29};
		double[] data2 = {2,197,70,45,543,30.5,0.158,53};
		MultiLayerNetwork model = KerasModelImport.
				importKerasSequentialModelAndWeights(simpleMlp);
		// make a random sample
		int inputs = 8;
		INDArray features = Nd4j.zeros(2, inputs);
		for (int i=0; i<inputs; i++) {
			features.putScalar(0, i, data[i]);
			features.putScalar(1, i, data2[i]);
		}
		// get the prediction
		double prediction = model.output(features).getDouble(1);
		System.out.println(prediction);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {

	}
}
