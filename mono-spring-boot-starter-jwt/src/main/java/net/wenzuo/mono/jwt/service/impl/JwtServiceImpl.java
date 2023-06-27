package net.wenzuo.mono.jwt.service.impl;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.wenzuo.mono.core.utils.JsonUtils;
import net.wenzuo.mono.jwt.service.JwtService;

/**
 * @author Catch
 * @since 2023-06-06
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

	private final JWSSigner jwsSigner;

	private final JWSVerifier jwsVerifier;

	@Override
	public <T> String sign(T payload) {
		Optional<JWSAlgorithm> jwsAlgorithm = jwsSigner.supportedJWSAlgorithms()
				.stream()
				.findFirst();
		if (jwsAlgorithm.isEmpty()) {
			throw new RuntimeException("JWSAlgorithm is empty");
		}
		JWSHeader jwsHeader = new JWSHeader(jwsAlgorithm.get());
		JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(JsonUtils.toJson(payload)));
		try {
			jwsObject.sign(jwsSigner);
			return jwsObject.serialize();
		}
		catch (JOSEException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parse(String signed, Class<T> clazz) {
		try {
			JWSObject jwsObject = JWSObject.parse(signed);
			if (!jwsObject.verify(jwsVerifier)) {
				return null;
			}
			if (clazz == String.class) {
				return (T) jwsObject.getPayload()
						.toString();
			}
			return JsonUtils.toObject(jwsObject.getPayload()
					.toString(), clazz);
		}
		catch (ParseException | JOSEException e) {
			return null;
		}
	}

}
