import cv2
import time
import numpy as np


def __angle_between(p1, p2):
    ang1 = np.arctan2(*p1[::-1])
    ang2 = np.arctan2(*p2[::-1])
    res = np.rad2deg((ang1 - ang2) % (2 * np.pi))
    return res


def get_angle(p1, p2, p3):
    ver1 = (p1[0] - p2[0], p1[1] - p2[1])
    ver2 = (p3[0] - p2[0], p3[1] - p2[1])
    res = __angle_between(ver1, ver2)
    res = (res + 360) % 360
    if res > 180:
        res = 360 - res
    return res


startTime = time.time()

#  각 파일 path
BODY_PARTS = {"Head": 0, "Neck": 1, "RShoulder": 2, "RElbow": 3, "RWrist": 4,
              "LShoulder": 5, "LElbow": 6, "LWrist": 7, "Chest": 8, "RHip": 9,
              "RKnee": 10, "RAnkle": 11, "LHip": 12, "LKnee": 13, "LAnkle": 14,
              "Background": 15}

POSE_PAIRS = [["Head", "Neck"], ["Neck", "RShoulder"], ["RShoulder", "RElbow"],
              ["RElbow", "RWrist"], ["Neck", "LShoulder"], ["LShoulder", "LElbow"],
              ["LElbow", "LWrist"], ["Neck", "Chest"], ["Chest", "RHip"], ["RHip", "RKnee"],
              ["RKnee", "RAnkle"], ["Chest", "LHip"], ["LHip", "LKnee"], ["LKnee", "LAnkle"]]

# 각 파일 path
protoFile = "pose_deploy_linevec_faster_4_stages.prototxt"
weightsFile = "pose_iter_160000.caffemodel"

# 위의 path에 있는 network 불러오기
net = cv2.dnn.readNetFromCaffe(protoFile, weightsFile)

# 이미지 읽어오기
image = cv2.imread("res/squat01.jpg")

# frame.shape = 불러온 이미지에서 height, width, color 받아옴
imageHeight, imageWidth, _ = image.shape

# network에 넣기위해 전처리
inpBlob\
    = cv2.dnn.blobFromImage(image, 1.0 / 255, (imageWidth, imageHeight), (0, 0, 0), swapRB=False, crop=False)

# network에 넣어주기
net.setInput(inpBlob)

# 결과 받아오기
output = net.forward()

# output.shape[0] = 이미지 ID, [1] = 출력 맵의 높이, [2] = 너비
H = output.shape[2]
W = output.shape[3]
print("이미지 ID : ", len(output), ", H : ", output.shape[2], ", W : ", output.shape[3])  # 이미지 ID

# 키포인트 검출시 이미지에 그려줌
points = []
angles = []  # 포인트 별 각도
for j in range(0, 8):  # 상체만: range(0, 9), 하체만: range(9, 16)
    # 해당 신체부위 신뢰도 얻음.
    probMap = output[0, j, :, :]

    # global 최대값 찾기
    minVal, prob, minLoc, point = cv2.minMaxLoc(probMap)

    # 원래 이미지에 맞게 점 위치 변경
    x = (imageWidth * point[0]) / W
    y = (imageHeight * point[1]) / H

    # 키포인트 검출한 결과가 0.1보다 크면(검출한곳이 위 BODY_PARTS랑 맞는 부위면) points에 추가, 검출했는데 부위가 없으면 None으로
    if prob > 0.1:
        cv2.circle(image, (int(x), int(y)), 3, (0, 255, 255), thickness=-1,
                   lineType=cv2.FILLED)  # circle(그릴곳, 원의 중심, 반지름, 색)
        cv2.putText(image, "{}".format(j), (int(x), int(y)), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 255), 1,
                    lineType=cv2.LINE_AA)
        points.append((int(x), int(y)))
    else:
        points.append(None)

endTime = time.time()
print("걸린 시간: " + (str)(endTime - startTime))

# cv2.imshow("Output-Keypoints", image)
# cv2.waitKey(0)

# 이미지 복사
imageCopy = image

# 각 POSE_PAIRS별로 선 그어줌 (머리 - 목, 목 - 왼쪽어깨, ...)
# for pair in POSE_PAIRS:
#     partA = pair[0]  # Head
#     partA = BODY_PARTS[partA]  # 0
#     partB = pair[1]  # Neck
#     partB = BODY_PARTS[partB]  # 1
#
#     # print(partA," 와 ", partB, " 연결\n")
#     if points[partA] and points[partB]:
#         cv2.line(imageCopy, points[partA], points[partB], (0, 255, 0), 2)

# 상체만
for j in range(0, 7):  # 해당 POSE_PAIRS에 따라 범위 변경
    partA = POSE_PAIRS[j][0]  # Head
    partA = BODY_PARTS[partA]  # 0
    partB = POSE_PAIRS[j][1]  # Neck
    partB = BODY_PARTS[partB]  # 1

    # print(partA, "와", partB, "연결\n")
    if points[partA] and points[partB]:
        cv2.line(imageCopy, points[partA], points[partB], (0, 255, 0), 2)

# for point in points:
#     print(point)

# 6-7(5,6,7), 3-4(2,3,4)
if points[2] and points[3] and points[4]:
    angle_RElbow = get_angle(points[2], points[3], points[4])
    print("오른쪽 팔꿈치: " + str(angle_RElbow))
if points[5] and points[6] and points[7]:
    angle_LElbow = get_angle(points[5], points[6], points[7])
    print("왼쪽 팔꿈치: " + str(angle_LElbow))

cv2.imshow("Output-Keypoints", imageCopy)
cv2.waitKey(0)
cv2.destroyAllWindows()

# 0, 2, 3, 4, 5, 7, 8(retry), 14, 15, 17, 18, 19, 20, 26, 27
