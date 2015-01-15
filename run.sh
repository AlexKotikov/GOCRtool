clear
INPUT_TXT="input.txt"
OUTPUT_TXT="output.txt"
RESULT="final.txt"
TEMP_IMAGE_NAME="temp_image"
TEMP_IMAGE_FORMAT="png"

cd ./bin
pwd
java -cp . GOCRtool pre ./../$INPUT_TXT 55 Monospaced  ./../$TEMP_IMAGE_NAME $TEMP_IMAGE_FORMAT
echo "GOCR start"
cd ..
gocr -i $TEMP_IMAGE_NAME.$TEMP_IMAGE_FORMAT  -o $OUTPUT_TXT -a 70
echo "GOCR done"
cd ./bin
java -cp . GOCRtool post ./../$OUTPUT_TXT ./../$RESULT
echo "done"
