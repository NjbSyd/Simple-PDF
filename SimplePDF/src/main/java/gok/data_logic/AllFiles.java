package gok.data_logic;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class AllFiles {
    private LinkedList<OneFile> oneFileList = new LinkedList<>();

    public AllFiles(List<File> files) {
        for (File currentFile : files) {
            String fileName = currentFile.getName();
            String filePath = currentFile.getAbsolutePath();
            String parentPath = currentFile.getParent();
            oneFileList.add(new OneFile(fileName, filePath, parentPath));
        }
    }

    public String[] getNames() {
        LinkedList<String> temp = new LinkedList<>();
        for (OneFile oneFile : oneFileList) {
            temp.add(oneFile.fileName);
        }
        return temp.toArray(new String[0]);
    }

    public String[] getPaths() {
        LinkedList<String> temp = new LinkedList<>();
        for (OneFile oneFile : oneFileList) {
            temp.add(oneFile.filePath);
        }
        return temp.toArray(new String[0]);
    }

    public String getParent(){
        return oneFileList.get(0).parentPath;
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
                OneFile file1 = oneFileList.get(index);
                OneFile file2 = oneFileList.get(index - 1);
                oneFileList.set(index - 1, file1);
                oneFileList.set(index, file2);
                index = index - 1;
            }
            case 'd' -> {
                if (index >= oneFileList.size() - 1) {
                    return index;
                }
                OneFile file1 = oneFileList.get(index);
                OneFile file2 = oneFileList.get(index + 1);
                oneFileList.set(index + 1, file1);
                oneFileList.set(index, file2);
                index = index + 1;
            }
        }
        return index;
    }

    public void appendMoreFiles(List<File> list) {
        for (File currentFile : list) {
            String fileName = currentFile.getName();
            String filePath = currentFile.getAbsolutePath();
            String parentPath = currentFile.getParent();
            oneFileList.add(new OneFile(fileName, filePath, parentPath));
        }
    }
}
