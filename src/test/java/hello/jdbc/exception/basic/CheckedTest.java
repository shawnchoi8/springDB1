package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {

    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow()) //service.callThrow 호출하면
                .isInstanceOf(MyCheckedException.class); //MyCheckedException 이 터진다.
    }

    /**
     * Exception을 상속받은 예외는 체크 예외가 된다.
     */
    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /**
     * Checked Exception has 2 options.
     * 1. catch the exception
     * 2. throw the exception
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * Catch Exception
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                //Exception handling logic
                log.info("exception caught, message={}", e.getMessage(), e);
            }
        }

        /**
         * Throw Exception (throws 없으면 compile error)
         *
         * @throws MyCheckedException
         */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }
    }

    static class Repository {
        public void call() throws MyCheckedException {
            throw new MyCheckedException("ex");
        }
    }

}
