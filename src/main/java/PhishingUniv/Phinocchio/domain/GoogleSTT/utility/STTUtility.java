package PhishingUniv.Phinocchio.domain.GoogleSTT.utility;

import PhishingUniv.Phinocchio.exception.GoogleSTT.STTAppException;
import PhishingUniv.Phinocchio.exception.GoogleSTT.STTErrorCode;
import java.io.File;

public class STTUtility {

    public static String convert(String fileName){

        String outputFileName = fileName.replaceAll("m4a", "wav");

        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg",
                "-i", fileName,
                outputFileName
        );

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("음성 파일 m4a -> wav 변환 성공");

                // m4a 파일 삭제
                File m4aFile = new File(fileName);
                if (m4aFile.exists()) {
                    if (m4aFile.delete()) {
                        System.out.println("m4a 파일 삭제 성공");
                    } else {
                        System.out.println("m4a 파일 삭제 실패");
                    }
                }
            } else {
                throw new STTAppException(STTErrorCode.FILE_UPLOAD_ERROR);
            }
        } catch (Exception e) {
            throw new STTAppException(STTErrorCode.FILE_UPLOAD_ERROR);
        }

        return outputFileName;

    }
}

