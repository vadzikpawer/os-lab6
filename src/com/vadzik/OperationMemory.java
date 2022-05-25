package com.vadzik;

import java.util.ArrayList;

public class OperationMemory {

    static final ArrayList<Procces> procceses = new ArrayList<>();
    private static final int size = 64;
    private static final int[] memory = new int[size];

    OperationMemory() {
        for (int i = 0; i < size; i++) {
            memory[i] = -1;
        }
    }

    public void addProcces(Procces newProcces) {
        procceses.add(newProcces);
        int startIndex = findFreeSpace(newProcces);
        if (startIndex == -1) {
            startIndex = moveToShMemory(newProcces);
            if (startIndex == -1) {
                System.out.println("Невозможно добавить новый процесс, недостаточно памяти");
                return;
            }
            for (int i = startIndex; i < startIndex + newProcces.size; i++) {
                memory[i] = newProcces.id;
            }
        } else {
            for (int i = startIndex; i < startIndex + newProcces.size; i++) {
                memory[i] = newProcces.id;
            }
        }
        procceses.get(procceses.size() - 1).startIndex = startIndex;
        procceses.get(procceses.size() - 1).typeOfMemory = 1;
        procceses.get(procceses.size() - 1).state = "Running";
    }

    static public void addProcces(int num) {
        int startIndex = findFreeSpace(procceses.get(num));
        if (startIndex == -1) {
            startIndex = moveToShMemory(procceses.get(num));
            if (startIndex == -1) {
                System.out.println("Невозможно добавить новый процесс, недостаточно памяти");
                return;
            }
            for (int i = startIndex; i < startIndex + procceses.get(num).size; i++) {
                memory[i] = procceses.get(num).id;
            }
        } else {
            for (int i = startIndex; i < startIndex + procceses.get(num).size; i++) {
                memory[i] = procceses.get(num).id;
            }
        }
        procceses.get(procceses.size() - 1).startIndex = startIndex;
        procceses.get(procceses.size() - 1).typeOfMemory = 1;
    }

    private static int findFreeSpace(Procces newProcces) {
        int startIndex = -1;
        int freeMemorySize = 0;
        for (int i = 0; i < size; i++) {
            if (memory[i] == -1 && startIndex == -1) {
                startIndex = i;
            } else if (memory[i] == -1) {
                freeMemorySize += 1;
            } else if (memory[i] != -1 && freeMemorySize < newProcces.size) {
                freeMemorySize = 0;
                startIndex = -1;
            }
            if (freeMemorySize >= newProcces.size) {
                return startIndex;
            }
        }
        return -1;
    }

    private static int moveToShMemory(Procces newProcces) {
        int startIndex = -1;
        for (Procces procces : procceses) {
            if (procces.state.equals("Wait") && procces.typeOfMemory == 1) {
                if (procces.size >= newProcces.size) {
                    startIndex = procces.startIndex;
                    for (int i = startIndex; i < startIndex + procces.size; i++) {
                        memory[i] = -1;
                    }
                    procces.typeOfMemory = 2;
                    procces.startIndex = -1;
                } else {
                    int freeMemory = procces.size;
                    for (int i = procces.startIndex + procces.size; i < size; i++) {
                        if (memory[i] == -1) {
                            freeMemory += 1;
                        } else {
                            break;
                        }
                        if (freeMemory >= newProcces.size) {
                            break;
                        }
                    }
                    if (freeMemory >= newProcces.size) {
                        startIndex = procces.startIndex;
                        for (int i = startIndex; i < startIndex + procces.size; i++) {
                            memory[i] = -1;
                        }
                        procces.typeOfMemory = 2;
                        procces.startIndex = -1;
                    }
                }

            }

        }
        return startIndex;
    }

    public void deleteFromOpMemory(int id) {
        if (id <= procceses.size() && id >= 0) {
            for (Procces procces : procceses) {
                if (procces.id == id) {
                    for (int i = procces.startIndex; i < procces.startIndex + procces.size; i++) {
                        memory[i] = -1;
                    }
                }
            }
            procceses.removeIf(x -> x.id == id);
        }
    }

    public void showStatusMemory() {
        for (int i = 0; i < 55; i++) System.out.print("_");
        System.out.println();
        System.out.printf("| %2s | %10s | %2s | %2s | %2s |\n", "id", "State", "St. index", "Size", "Type of memory");
        for (int i = 0; i < 55; i++) System.out.print("-");
        System.out.println();
        for (Procces procces : procceses) {
            System.out.printf("| %2d | %10s | %9d | %4d | %14d |\n", procces.id, procces.state, procces.startIndex, procces.size, procces.typeOfMemory);
            for (int i = 0; i < 55; i++) System.out.print("-");
            System.out.println();
        }
        System.out.println("Type of memory: 1 - оперативная память, 2 - основная память");
    }

    public void pauseProcces(int id) {
        if (id <= procceses.size() && id >= 0) {
            procceses.get(id).pauseProcces();
        }
    }
}
