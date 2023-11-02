package APP.LOGIC;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class AllFiles {
    private final LinkedList<OneFile> oneFileList = new LinkedList<>();
    private static AllFiles instance;

    private AllFiles() {

    }

    public static AllFiles getInstance() {
        if (instance == null) {
            instance = new AllFiles();
        }
        return instance;
    }

    public String[] getNames() {
        String[] temp = new String[oneFileList.size()];
        for (int i = 0; i < oneFileList.size(); i++) {
            temp[i] = oneFileList.get(i).fileName;
        }
        return temp;
    }

    public String[] getPaths() {
        String[] temp = new String[oneFileList.size()];
        for (int i = 0; i < oneFileList.size(); i++) {
            temp[i] = oneFileList.get(i).filePath;
        }
        return temp;
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

    public void AddFiles(List<File> list) {
        boolean isListEmpty = oneFileList.isEmpty();
        List<String> names = isListEmpty ? null : List.of(getNames());
        for (File currentFile : list) {
            String fileName = currentFile.getName();
            String filePath = currentFile.getAbsolutePath();
            String parentPath = currentFile.getParent();
            OneFile file = new OneFile(fileName, filePath, parentPath);
            if (!isListEmpty) {
                if (!names.contains(fileName)) {
                    oneFileList.add(file);
                }
            } else {
                oneFileList.add(file);
            }
        }
    }

    public void clearFilesList() {
        if (!oneFileList.isEmpty())
            oneFileList.clear();

    }
}
