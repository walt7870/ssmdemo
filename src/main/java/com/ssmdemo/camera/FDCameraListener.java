package com.ssmdemo.camera;
public interface FDCameraListener {
	public void OnCameraData(String clientAddress, FDCameraData data);
	public void onSocketSessionClose(String clientAddress);
}
