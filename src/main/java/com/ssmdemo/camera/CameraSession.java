package com.ssmdemo.camera;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CameraSession {
    private static final Logger logger = LoggerFactory.getLogger(CameraSession.class);
    private static final Logger frlog = LoggerFactory.getLogger("FRPERFORMANCE");
    private static final int DEQUE_MAX_LENGTH = 10;//buffer size
    private String mac = null;
    private boolean bindViewer = false;
    private ArrayDeque<FDCameraData> buffer = null;
    private int count = 0;
    private static final int PROCESS_FRAME_INTERVAL = 2;
//    private static final float FR_CONFIDENCE_THRESHOLD = Constant.FR_CONFIDENCE_THRESHOLD;
    private static final ThreadLocal<SimpleDateFormat> sdfs = new ThreadLocal<>();
    private boolean online = true;


//    private CopyOnWriteArraySet<VideoViewer> viewers;
    private long latestOnline = 0l;

    public CameraSession(FDCameraData d) {
        buffer = new ArrayDeque<FDCameraData>();
        mac = d.mStrMac;
        online = true;
//        viewers = new CopyOnWriteArraySet<>();
        latestOnline = System.currentTimeMillis();

    }

    public String getMac() {
        return mac;
    }

//	public void analyseAndDrawFRBox(FDCameraData d) {
//		List<FaceBox> faces = analyse(d ,false , false);
//		drawFaceBoxs(d , faces);
//		notifyViewers(d);
//	}

    public void addLast(FDCameraData d) {
//		synchronized(data) {
//			count++;
//			if(count >= PROCESS_FRAME_INTERVAL) {
//        List<FaceBox> faces = analyse(d, false, false);
//				if(bindViewer) {
//					if(buffer.size() >= DEQUE_MAX_LENGTH)
//						buffer.poll();
        //draw faces
//        drawFaceBoxs(d, faces);
//        notifyViewers(d);
//					insertBuffer(d);
//					buffer.add(d);
//					logger.info("{} Queue size = {}",d.mStrMac , buffer.size());
//				}
//				count = 0;
//			}
//		}
    }

    public void notifyWithFaces(FDCameraData d) {

    }

    public void insertBuffer(FDCameraData d) {
        if (buffer.size() >= DEQUE_MAX_LENGTH)
            buffer.poll();
        buffer.add(d);
        logger.info("{} Queue size = {}", d.mStrMac, buffer.size());
    }


    public FDCameraData pollFirst() {
        return buffer.poll();
    }


    public List<Object> analyse(FDCameraData d, boolean drawFaceBox, boolean notifyViewer) {
        if (!d.mHasNewFd) return Collections.emptyList();
        if (d.mFaceNum == 0) return Collections.emptyList();
//        StringBuilder
//        for(int i=0;i<d.mFaceNum;i++){
//
//        }

//直接将图片转发到代理
//        try {
//            String url=Constant.PROXY_IDENTIFY;
//            HttpResponse response = Request.Post(url)
////                    .connectTimeout(50000)
////                    .socketTimeout(90000)
//    //                .addHeader("Authorization", "Token " + ntechToken)//Token 4BBj-6pjv
//                    .body(MultipartEntityBuilder
//                            .create()
//                            .addTextBody("mf_selector", "all")
//                            .addTextBody("cam_id", d.mStrMac)
//    //                        .addTextBody("bbox", )
//                            .addBinaryBody("photo", d.mJpgData, ContentType.create("image/jpeg"), "photo.jpg")
//                            .build())
//                    .execute().returnResponse();
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//        return EntityUtils.toString(response.getEntity());

        //使用拓展处理逻辑,不依赖代理处理
//        frlog.debug("Doing FR ", d.toString());
//        long start = System.currentTimeMillis();
//        List<FaceBox> fbs = new ArrayList<>();
//        NTechIdentifyResponse analysis = nTechFRService.analyze(d);
//
////        frlog.debug("{} Cost = {} millis", d.mStrMac, System.currentTimeMillis() - start);
//        if (analysis == null) {
////            logger.debug("NTechIdentifyResponse is null");
//            return Collections.emptyList();
//        }
//        Map<String, List<NTechFaceMatch>> result = analysis.getResults();
//        if (result == null) {
////            logger.debug("NTechFaceMatch is null");
//            return Collections.emptyList();
//        }
//        for (String key : result.keySet()) {
//            String faceBox = key.substring(1, key.length() - 1);
//            String[] faceBoxSplit = faceBox.split(",");
//            int x1 = Integer.parseInt(faceBoxSplit[0].trim());
//            int y1 = Integer.parseInt(faceBoxSplit[1].trim());
//            int x2 = Integer.parseInt(faceBoxSplit[2].trim());
//            int y2 = Integer.parseInt(faceBoxSplit[3].trim());
//            int w = x2 - x1;
//            int h = y2 - y1;
//
////            try {
////                ByteArrayInputStream in = new ByteArrayInputStream(d.mJpgData);
////                BufferedImage imageHandle = ImageIO.read(in);
////                ImageIO.write(imageHandle, "jpeg", new File("/home/n-tech-admin/images/"+System.currentTimeMillis()));
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//            if (w < 0)
//                throw new RuntimeException("NTech returned face with w<0; x1,y1,x2,y2 = " + faceBox);
//            if (h < 0)
//                throw new RuntimeException("NTech returned face with h<0; x1,y1,x2,y2 = " + faceBox);
//            List<NTechFaceMatch> list = result.get(key);
//
//            NTechFaceMatch bestMatch = null;
//
//            if (list.size() > 0) {
//                for (NTechFaceMatch m : list) {
//                    if (bestMatch == null || m.getConfidence() > bestMatch.getConfidence()) {
//                        bestMatch = m;
//                    }
//                }
//            }
//
//            if (bestMatch != null && bestMatch.getConfidence() > FR_CONFIDENCE_THRESHOLD && !Strings.isNullOrEmpty(bestMatch.getFace().getMeta()))//found
//            {
//                FaceBox fb = new FaceBox(x1, y1, w, h, bestMatch.getFace().getMeta());
//                fbs.add(fb);
//            }

//			else if(bestMatch != null){//low confidence
//            		FaceBox fb = new FaceBox(x1, y1, w, h, bestMatch.getFace().getMeta());
//				fbs.add(fb);
//            }else {//unknown, how to deal with unknown
//            		FaceBox fb = new FaceBox(x1, y1, w, h, "Unknown");
//				fbs.add(fb);
//            }

//            // draw face box
//            if (drawFaceBox) drawFaceBoxs(d, fbs);
//            // notify viewer
//            if (notifyViewer) notifyViewers(d);
//
//            // save fr result to database
//            try {
//                saveToDatabase(d, bestMatch, x1, y1, w, h);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (config.isSaveFR2DB()) {
//                try {
////					long ss = System.currentTimeMillis();
//                    saveToDatabase(d, bestMatch);
////					logger.info("FR save databse time cost = {} millis", System.currentTimeMillis() - ss);
//                } catch (Exception e) {
//                    logger.error("FR result save to dabase error :" + e.getMessage(), e);
//                    e.printStackTrace();
//                }
//            }
//        }
//        frlog.info("{} FR result,{} ", d.mStrMac, fbs.toString());
//        return fbs;
//        return Collections.emptyList();

        return null;
    }



    //identify结果没有 save to snapshot
    private void saveToSnapshot(FDCameraData d) {
//        logger.debug("detected face identify result is empty,save as guest");
//        try {
//            TbIpc camera = ipcService.getByAddress(d.mStrMac);
//            if (camera == null) throw new HException("Camera " + d.mStrMac + " not exists in database.");
//            InputSnapshotVo snapshotVo = new InputSnapshotVo();
//            //snapshotVo.setCamera_id(camera.getId().toString());
//            String cam_folder = camera.getAddress().replaceAll(":", "");
////            Path path = saveSnapshotToFileSystem(d, cam_folder,null);
//            snapshotVo.setCamera_id(camera.getCameraId().toString());
//            snapshotVo.setIpc_id(camera.getId());
//            snapshotVo.setDoor_id(camera.getDoorId());
//            snapshotVo.setSnapshot_photo(path.toString());
//            snapshotVo.setGusetCode(null);
//            snapshotService.add(snapshotVo);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
    }


    private Path saveSnapshotToFileSystem(FDCameraData data, String cameraName, int x1, int y1, int width, int height) {
        SimpleDateFormat sdf = sdfs.get();
        if (sdf == null) {
//            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdfs.set(sdf);
        }
        String snapAndCamera =  "/home/share" + cameraName;
        Date date = new Date();
        createFolder("/home/share", cameraName);
        createFolder(snapAndCamera, sdf.format(date));
        String fileName = System.currentTimeMillis() + ".jpg";
//		String fileName = sdf.format(new Date(data.timestamp)) + ".jpg";
        Path path = Paths.get("/home/share", cameraName, sdf.format(date), fileName);
        ByteArrayInputStream in = null;
        try {
            BufferedImage image = cutImg(data, x1, y1, width, height);
            ImageIO.write(image, "jpeg", path.toFile());
        } catch (IOException e) {
            logger.error("Failed to save snapshot image file in " + path.toString());
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return path;
    }

    private void createFolder(String base, String cameraName) {
        Path path = Paths.get(base, cameraName);
        if (!path.toFile().exists())
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                logger.error("Failed to create diectory " + path.toString());
                e.printStackTrace();
            }
    }
//
//    private void drawFaceBoxs(FDCameraData d, List<FaceBox> faces) {
//        try {
//            if (faces != null && faces.size() > 0) {
//                BufferedImage bfimg = createBufferedImage(d.mJpgData);
//                drawBoxesOnImage(faces, bfimg);
//                d.mJpgData = convertBufferedImage2Byte(bfimg);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    protected void drawBoxesOnImage(List<FaceBox> faces, BufferedImage bgImage)
//            throws IOException {
//        Graphics graph = bgImage.getGraphics();
//        graph.setColor(Color.RED);
//        for (FaceBox face : faces) {
//            graph.drawRect(face.getX(), face.getY(), face.getW(), face.getH());
//            String name = face.getMeta();
//            graph.drawString(name, face.getX(), face.getY() - 10);
//        }
//        graph.dispose();
//    }

    protected BufferedImage createBufferedImage(byte[] jpg) {
        BufferedImage jpgImage = null;
        try {
            jpgImage = ImageIO.read(new ByteArrayInputStream(jpg));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jpgImage;
    }

    protected byte[] convertBufferedImage2Byte(BufferedImage jpgImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(jpgImage, "jpeg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
        if (online) latestOnline = System.currentTimeMillis();
    }

    public boolean isBindViwer() {
        return bindViewer;
    }

    public void setBindViwer(boolean bind) {
        this.bindViewer = bind;
//		if(bindViewer) {
//			data.clear();
//		}
    }

//    public void notifyViewers(FDCameraData data) {
//        for (VideoViewer v : viewers) {
//            v.notify(data);
//        }
//    }

//    public void registerVideoViewer(VideoViewer viewer) {
//        if (!viewers.contains(viewer)) viewers.add(viewer);
//    }
//
//    public void unregisterVideoViewer(VideoViewer viewer) {
//        viewers.remove(viewer);
//    }
//
//    public boolean hasViewer() {
//        return viewers.size() > 0;
//    }

    public long getLatestOnline() {
        return latestOnline;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mac == null) ? 0 : mac.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CameraSession other = (CameraSession) obj;
        if (mac == null) {
            if (other.mac != null)
                return false;
        } else if (!mac.equals(other.mac))
            return false;
        return true;
    }

    private BufferedImage cutImg(FDCameraData fdCameraData, int x, int y, int width, int height) {

        ByteArrayInputStream in = null;
        BufferedImage imageHandle = null;
        try {

            in = new ByteArrayInputStream(fdCameraData.mJpgData);
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpeg");
            ImageReader reader = readers.next();
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(in);
            reader.setInput(imageInputStream, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);
            imageHandle = reader.read(0, param);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return imageHandle;
    }

}
