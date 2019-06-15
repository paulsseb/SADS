from __future__ import division
from scipy.spatial import distance as dist
from imutils import perspective
from imutils import contours
import imutils
import cv2
import sys
import os
import argparse
import numpy as np
import time

try:
    #used to compute the midpoint between two sets of (x, y)-coordinates.
    def midpoint(ptA, ptB):
        return ((ptA[0] + ptB[0]) * 0.5, (ptA[1] + ptB[1]) * 0.5)
    # sys.path.append('../src')
    # construct the argument parse and parse the arguments
    print("calling python algorithm")
    ap = argparse.ArgumentParser()
    ap.add_argument("-i", "--image", required=True,
	    help="path to the input image")
    ap.add_argument("-u", "--userid", required=True,
	help="supply the userid that will be saved on the image")	
    # how the args will be passed
    #$ python RANoduleAlgo.py --image images/example_02.png 
    args = vars(ap.parse_args())


    #  load the model config and the model into memory. 
    protoFile = "C:/xampp/htdocs/SadsWeb/uploadedFiles/handmodel/pose_deploy.prototxt"
    weightsFile = "C:/xampp/htdocs/SadsWeb/uploadedFiles/handmodel/pose_iter_102000.caffemodel"
    nPoints = 22
    POSE_PAIRS = [ [0,1],[1,2],[2,3],[3,4],[0,5],[5,6],[6,7],[7,8],[0,9],[9,10],[10,11],[11,12],[0,13],[13,14],[14,15],[15,16],[0,17],[17,18],[18,19],[19,20] ]
    net = cv2.dnn.readNetFromCaffe(protoFile, weightsFile)
    print("Loading hand model and config file")
    # load the image, convert it to grayscale, and blur it slightly
    image = cv2.imread(args["image"]) 
    uid = args["userid"] 
    print("img uploaded and uid got")
    # we need to keep in mind aspect ratio so the image does
    # not look skewed or distorted -- therefore, we calculate
    # the ratio of the new image to the old image
    r = 400.0 / image.shape[1]
    dim = (400, int(image.shape[0] * r))

    # perform the actual resizing of the image and show it
    resized = cv2.resize(image, dim, interpolation = cv2.INTER_AREA)
    #cv2.imshow("resized", resized)
    # Using a parameter of “0” indicates that any keypress will un-pause the execution.
    #cv2.waitKey(0)

    edged = cv2.Canny(resized, 10, 250)
    #cv2.imshow("Edges", edged)
    #cv2.waitKey(0)
 
    #applying closing function 
    kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (7, 7))
    closed = cv2.morphologyEx(edged, cv2.MORPH_CLOSE, kernel)
    #cv2.imshow("Closed", closed)
    #cv2.waitKey(0)
 
    #finding_contours 
    (cnts, _) = cv2.findContours(closed.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
     #resized will be the new image with the a4 paper outlined for easy identification on the next find contours
    for c in cnts:
        rect = cv2.minAreaRect(c)
        box = cv2.boxPoints(rect)
        # convert all coordinates floating point values to int
        box = np.int0(box)
        # draw a green 'nghien' rectangle
        cv2.drawContours(resized, [box], 0, (0, 255, 0),2)


        #cv2.imshow("Output", resized)
        #cv2.waitKey(0)

    gray=cv2.cvtColor(resized,cv2.COLOR_BGR2GRAY)
    edged = cv2.Canny(resized, 10, 250)
    (cnts, _) = cv2.findContours(edged.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    idx = 0
     #for c in cnts:
     #return new_image
    print("identifying  a4 paper")
    x,y,w,h = cv2.boundingRect( cnts[0])
    if w>100 and h>100:
        #idx+=1
        new_img=resized[y:y+h,x:x+w]
        #cv2.imwrite(str(idx) + '.jpg', new_img)
        #cv2.imshow("im",new_img)
        #cv2.waitKey(0)
        #Continue with the diagnosis process identify hand points
        box = cv2.minAreaRect(cnts[0])
        box = cv2.cv.BoxPoints(box) if imutils.is_cv2() else cv2.boxPoints(box)
        box = np.array(box, dtype="int")

	    # order the points in the contour such that they appear
	    # in top-left, top-right, bottom-right, and bottom-left
	    # order, then draw the outline of the rotated bounding
	    # box

        box = perspective.order_points(box)
        cv2.drawContours(resized, [box.astype("int")], -1, (0, 255, 0), 2)

        # unpack the ordered bounding box, then compute the midpoint
        # between the top-left and top-right coordinates, followed by
        # the midpoint between bottom-left and bottom-right coordinates
        (tl, tr, br, bl) = box


        # unpack the ordered bounding box, then compute the midpoint
        # between the top-left and top-right coordinates, followed by
        # the midpoint between bottom-left and bottom-right coordinates

        (tltrX, tltrY) = midpoint(tl, tr)
        (blbrX, blbrY) = midpoint(bl, br)
 
        # compute the midpoint between the top-left and top-right points,
        # followed by the midpoint between the top-righ and bottom-right
        (tlblX, tlblY) = midpoint(tl, bl)
        (trbrX, trbrY) = midpoint(tr, br)

         # compute the Euclidean distance between the midpoints
        dA = dist.euclidean((tltrX, tltrY), (blbrX, blbrY))
        dB = dist.euclidean((tlblX, tlblY), (trbrX, trbrY))
 
        # if the pixels per metric has not been initialized, then
        # compute it as the ratio of pixels to supplied metric
        # (in this case, inches)
          # pixelsPerMetric = dB / args["width"]
        #if pixelsPerMetric is None:
        pixelsPerMetric = dB / 210

        #Detetect hand joints using cropped image
        frame = cv2.resize(new_img, dim, interpolation = cv2.INTER_AREA)
        frameCopy = np.copy(frame)
        frameWidth = frame.shape[1]
        frameHeight = frame.shape[0]
        aspect_ratio = frameWidth/frameHeight


        threshold = 0.1
        t = time.time()
        # input image dimensions for the network convert the BGR image to blob so that it can be fed to the network. 
        inHeight = 368
        inWidth = int(((aspect_ratio*inHeight)*8)//8)

        # convert the BGR image to blob so that it can be fed to the network and get predictions
        inpBlob = cv2.dnn.blobFromImage(frame, 1.0 / 255, (inWidth, inHeight), (0, 0, 0), swapRB=False, crop=False)
        net.setInput(inpBlob)
        output = net.forward()
        #print("time taken by network : {:.3f}".format(time.time() - t))

        # Show Detections
        #The output has 22 matrices with each matrix being the Probability Map of a keypoint.
        #For finding the exact keypoints, first, we scale the probabilty map to the size of the original image. 
        # Then find the location of the keypoint by finding the maxima of the probability map.
        # This is done using the minmaxLoc function in OpenCV. We draw the detected points along with the numbering on the image.
        # 
        # Empty list to store the detected keypoints
        points = []
        print("identifying hand joints")

        for i in range(nPoints):
            # confidence map of corresponding body's part.
            probMap = output[0, i, :, :]
            probMap = cv2.resize(probMap, (frameWidth, frameHeight))

            # Find global maxima of the probMap.
            minVal, prob, minLoc, point = cv2.minMaxLoc(probMap)

            if prob > threshold :
                # this is where thickness will be computed froom..
                #cv2.circle(frameCopy, (int(point[0]), int(point[1])), 8, (0, 255, 255), thickness=-1, lineType=cv2.FILLED)
                #cv2.putText(frameCopy, "{}".format(i), (int(point[0]), int(point[1])), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2, lineType=cv2.LINE_AA)

                # Add the point to the list if the probability is greater than the threshold
                points.append((int(point[0]), int(point[1])))
                #cv2.imshow('Output-Keypoints', frameCopy)
                #cv2.waitKey(0)
            else :
                points.append(None)

        #We will use the detected points to get the skeleton formed by the keypoints and draw them on
        #the image.
        # Draw Skeleton
        for pair in POSE_PAIRS:
            partA = pair[0]
            partB = pair[1]
            # check curent partA and partB and match to given joint in order to measure thickness.
            if points[partA] and points[partB]:
                cv2.line(frame, points[partA], points[partB], (0, 255, 255), 2)
                cv2.circle(frame, points[partA], 8, (0, 0, 255), thickness=-1, lineType=cv2.FILLED)
                cv2.circle(frame, points[partB], 8, (0, 0, 255), thickness=-1, lineType=cv2.FILLED)

        #cv2.imshow('Output-Keypoints', frameCopy)
         #cv2.imshow('Output-Skeleton', frame)


        #cv2.imwrite('Output-Keypoints.jpg', frameCopy)
        cv2.imwrite('C:/xampp/htdocs/SadsWeb/uploadedFiles/finished/'+uid+'.jpg', frame)
        print("image analysis complete ")

        #print("Total time taken : {:.3f}".format(time.time() - t))

        cv2.waitKey(0)




    else:
        print("A4 PAPER NOT FOUND")


except Exception as exp:
  print("Error")
  print(exp)
