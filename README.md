# GOCRtool

The GOCR tool
-------------

This program has purpose to bypass a unicode defense that is in one site for coders. This site keeps code snippets but doesn't allow to run them.

The matter of defense on that site is to replace alike latin characters in the parts of code with ones from whole unicode table. Humans can read such code, because letters from different languages like "i","o" (and others) look similar,  but they cannot be compiled by Java properly. 
My solution is to bypass the defense by using an OCR program.
But it is very inconveniently to run the OCR via UI every time when I need to receive operable Java code, also the text after the OCR is in need to be processed, because GOCR-core cannot recognize all letters properly. And I've decided to write this tool.

This tool realizes pre-parsing of code, converts text to a jpg format, then a sh script sends it to an OCR (GOCR), takes back, does post-parsing and then, extracted code is ready to be compiled.   

You need gocr v 0.49 installed.
sudo apt-get install gocr

It takes text from input.txt
The result is in the final.txt

In order to run it use command: sh run.sh


my first useful Java  program =)
