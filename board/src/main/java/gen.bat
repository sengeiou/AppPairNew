@echo off

rem rd /s /q com\lys\protobuf
rem ..\..\..\..\kit\protoc.exe -I=. --java_out=. protocol_book.proto

set command=C:\wangzhiting\work\code\client\tools\command.jar
set protoc=C:\wangzhiting\work\code\client\tools\protoc.exe
rem set inDir=C:\wangzhiting\work\code\client\proto
set inDir=C:\wangzhiting\work\code\client\spring\zjyk\proto
set outDir=%cd%
set packageName=com.lys.protobuf

set filenames=+
set filenames=%filenames%+protocol_common.proto
set filenames=%filenames%+protocol_note.proto
set filenames=%filenames%+protocol_board.proto
set filenames=%filenames%+protocol_app.proto
set filenames=%filenames%+protocol_pair.proto
set filenames=%filenames%+protocol_pair2.proto
set filenames=%filenames%+protocol_shop.proto
set filenames=%filenames%+protocol_score.proto
set filenames=%filenames%+protocol_zhixue.proto
set filenames=%filenames%+protocol_teach.proto
set filenames=%filenames%+protocol_live.proto

java -jar %command% genAndroidProto %protoc% %inDir% %outDir% %packageName% %filenames%

rem echo press any key to exit ...
rem pause>nul
