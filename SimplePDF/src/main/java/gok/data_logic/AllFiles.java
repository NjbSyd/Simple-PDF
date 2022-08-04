package gok.data_logic;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class AllFiles {
    LinkedList<oneFile> oneFileList = new LinkedList<>();

    public AllFiles(List<File> files) {
        for (File currentFile : files) {
            String fileName = currentFile.getName();
            String filePath = currentFile.getAbsolutePath();
            oneFileList.add(new oneFile(fileName, filePath));
        }
    }


    public String[] getNames() {
        LinkedList<String> temp = new LinkedList<>();
        for (oneFile oneFile : oneFileList) {
            temp.add(oneFile.fileName);
        }
        return temp.toArray(new String[0]);
    }

    public void remove(int index) {
        oneFileList.remove(index);
    }

    public int move(int index, char direction) {
        switch (direction) {
            case 'u' -> {
                if (index == 0) {
                    return index;
                }
                oneFile file1 = oneFileList.get(index);
                oneFile file2 = oneFileList.get(index - 1);
                oneFileList.set(index - 1, file1);
                oneFileList.set(index, file2);
                index = index - 1;
            }
            case 'd' -> {
                if (index >= oneFileList.size() - 1) {
                    return index;
                }
                oneFile file1 = oneFileList.get(index);
                oneFile file2 = oneFileList.get(index + 1);
                oneFileList.set(index + 1, file1);
                oneFileList.set(index, file2);
                index = index + 1;
            }
        }
        return index;

    }
}
