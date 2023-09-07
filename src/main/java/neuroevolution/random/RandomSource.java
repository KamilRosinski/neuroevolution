package neuroevolution.random;

public interface RandomSource {

    long getSeed();
    int nextBits(int numberOfBits);

}
