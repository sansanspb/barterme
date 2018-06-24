package ru.iqdevelop.barterme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.iqdevelop.barterme.entities.PhotoEntity;
import ru.iqdevelop.barterme.repositories.PhotoRepository;
import ru.iqdevelop.barterme.utils.RandomString;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@EnableTransactionManagement
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Transactional
    public PhotoEntity saveImage(String filename, byte[] bytes) {
        validateBytes(bytes);

        File newFile = saveBytesToFile(filename, bytes);

        PhotoEntity newPhoto = new PhotoEntity();
        newPhoto.setOriginalPath(newFile.getAbsolutePath());
        photoRepository.insert(newPhoto);

        return newPhoto;
    }


    /*
    String realPath = file.getParent();
		byte[] bytes = null;

		RandomString rnd = new RandomString(10);
		File newFile = new File(realPath + rnd.nextString() + "_" + filename);

		try {
			bytes = FileUtil.getBytes(file);
		} catch (FileNotFoundException e) {
			logger.error("File Not Found.");
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error("Error Reading The File.");
			throw new RuntimeException(e);
		}

		try (FileInputStream fileInputStream = new FileInputStream(file);
				FileOutputStream fileOutputStream = new FileOutputStream(newFile);) {
			if ((bytes != null) && (bytes.length > 0)) {
				int count = fileInputStream.read(bytes);
				if (count != -1) {
					fileOutputStream.write(bytes, 0, count);
				}
			}

			return newFile.getPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
     */

    private File saveBytesToFile(String filename, byte[] bytes) {

        File realPath = getFileDirectory();


        RandomString rnd = new RandomString(10);
        String newFilename = rnd.nextString() + "_" + filename;

        // Create the file on server
        File newFile = new File(realPath.getAbsolutePath() + File.separator + newFilename);
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try (FileOutputStream fileOutputStream = new FileOutputStream(newFile)) {
            if ((bytes != null) && (bytes.length > 0)) {
                int count = bytes.length;
                if (count != -1) {
                    fileOutputStream.write(bytes, 0, count);
                }
            }

            return newFile;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateBytes(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            throw new RuntimeException("Файл пуст");
        }
    }

    public File getFileDirectory() {
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "tmpFiles");
        if (!dir.exists())
            dir.mkdirs();

        return dir;
    }

    public File getTempFilePath(String imageName) {
        return new File(getFileDirectory().getAbsolutePath() + File.separator + imageName);
    }
}
