package hei.school.sarisary.endpoint;

import hei.school.sarisary.service.sary.SaryService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;

@AllArgsConstructor
@ExtendWith(MockitoExtension.class)
public class SaryControllerTest {

    @Mock
    private SaryService saryService;
    SaryController saryController;

    @Test
    public void testSend() throws IOException {
    }

    @Test
    public void testReceive() throws IOException {
    }
}
