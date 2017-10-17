import java.util.Comparator;

public class PriorityQueue {


    public static void main(String[] args) {
        Patient patient1 = new Patient("Zeiad", 5);
        Patient patient2 = new Patient("Ashish", 2);
        Patient patient3 = new Patient("Ali", 9);
        Patient patient4 = new Patient("Naweed", 7);

        MyPriorityQueue<Patient> priorityQueue = new MyPriorityQueue<Patient>(new PatientComparator());
        priorityQueue.enqueue(patient1);
        priorityQueue.enqueue(patient2);
        priorityQueue.enqueue(patient3);
        priorityQueue.enqueue(patient4);

        while (priorityQueue.getSize() > 0)
            System.out.print(priorityQueue.dequeue() + " ");
    }

    static class Patient implements Comparable<Patient> {
        private String name;
        private int priority;

        public Patient(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return name + "(priority:" + priority + ")";
        }

        public int compareTo(Patient o) {
            return this.priority - o.priority;
        }
    }

    static class PatientComparator implements Comparator<Patient> {
        @Override
        public int compare(Patient o1, Patient o2) {
            return o1.priority - o2.priority;
        }
    }

    static class MyPriorityQueue<E> {
        private Heap<E> heap;

        MyPriorityQueue(Comparator<? super E> comparator) {
            heap = new Heap<E>(comparator);
        }

        public void enqueue(E newObject) {
            heap.add(newObject);
        }

        public E dequeue() {
            return heap.remove();
        }

        public int getSize() {
            return heap.getSize();
        }
    }

    static class Heap<E> {
        private java.util.ArrayList<E> list = new java.util.ArrayList<E>();
        private Comparator<? super E> comparator;


        public Heap(Comparator<? super E> comparator) {
            this.comparator = comparator;
        }


        public Heap(E[] objects, Comparator<? super E> comparator) {
            this.comparator = comparator;
            for (int i = 0; i < objects.length; i++)
                add(objects[i]);
        }


        public void add(E newObject) {
            list.add(newObject);
            int currentIndex = list.size() - 1;

            while (currentIndex > 0) {
                int parentIndex = (currentIndex - 1) / 2;

                if (comparator.compare(list.get(currentIndex), list.get(parentIndex)) > 0) {
                    E temp = list.get(currentIndex);
                    list.set(currentIndex, list.get(parentIndex));
                    list.set(parentIndex, temp);
                } else
                    break;

                currentIndex = parentIndex;
            }
        }


        public E remove() {
            if (list.size() == 0)
                return null;

            E removedObject = list.get(0);
            list.set(0, list.get(list.size() - 1));
            list.remove(list.size() - 1);

            int currentIndex = 0;
            while (currentIndex < list.size()) {
                int leftChildIndex = 2 * currentIndex + 1;
                int rightChildIndex = 2 * currentIndex + 2;


                if (leftChildIndex >= list.size())
                    break;
                int maxIndex = leftChildIndex;
                if (rightChildIndex < list.size()) {
                    if (comparator.compare(list.get(maxIndex), list.get(rightChildIndex)) < 0) {
                        maxIndex = rightChildIndex;
                    }
                }


                if (comparator.compare(list.get(currentIndex), list.get(maxIndex)) < 0) {
                    E temp = list.get(maxIndex);
                    list.set(maxIndex, list.get(currentIndex));
                    list.set(currentIndex, temp);
                    currentIndex = maxIndex;
                } else
                    break;
            }

            return removedObject;
        }


        public int getSize() {
            return list.size();
        }
    }

}