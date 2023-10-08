package pl.dev4lazy.waste;

public interface Decoder<D,R> {

    R decode(D data );

}
