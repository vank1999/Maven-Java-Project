package com.example.javamavenjunithelloworld;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({System.class, HelloApp.class})
public class HelloAppTest {

    @Test
    public void testMain() {
        String[] args = {"1"};
        HelloApp.main(args);
    }

    @Test
    public void testWrongArgument() {
        PowerMockito.mockStatic(System.class);

        String[] args = {"bicycle"};
        HelloApp.main(args);

        // Verify that System.exit was called with the expected status code
        PowerMockito.verifyStatic(System.class, times(1));
        System.exit(HelloApp.EXIT_STATUS_PARAMETER_NOT_UNDERSTOOD);
    }

    @Test
    public void testHelloError() throws Exception {
        PowerMockito.mockStatic(System.class);

        // Mock Hello used by HelloApp to throw the expected exception when invoked with setTimes(5).
        Hello hi = mock(Hello.class);
        doThrow(new IllegalArgumentException("Nope.")).when(hi).setTimes(5);
        // Sneakily insert our fake Hello class when it is created.
        whenNew(Hello.class).withNoArguments().thenReturn(hi);

        // We know this will raise the expected exception, because we mocked Hello.
        String[] args = {"5"};
        HelloApp.main(args);

        // Verify that System.exit was called with the expected status code
        PowerMockito.verifyStatic(System.class, times(1));
        System.exit(HelloApp.EXIT_STATUS_HELLO_FAILED);
    }

    @Test
    public void testDefaultArgument() {
        String[] args = {};
        HelloApp.main(args);
    }
}
