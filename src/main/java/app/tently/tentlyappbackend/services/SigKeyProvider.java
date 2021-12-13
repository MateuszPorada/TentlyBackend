package app.tently.tentlyappbackend.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Data
public class SigKeyProvider {
    @Value("${SIGNATURE.KEY}")
    private String sigKey;
}
