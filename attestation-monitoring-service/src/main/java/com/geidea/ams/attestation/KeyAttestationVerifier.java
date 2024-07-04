
package com.geidea.ams.attestation;

import com.geidea.ams.android.CertificateRevocationStatus;
import com.geidea.ams.android.ParsedAttestationRecord;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import static com.geidea.ams.android.Constants.*;
import static com.geidea.ams.android.ParsedAttestationRecord.createParsedAttestationRecord;
import static java.nio.charset.StandardCharsets.UTF_8;

public class KeyAttestationVerifier {

    private static final Logger LOG = LoggerFactory.getLogger(KeyAttestationVerifier.class);

    public static ParsedAttestationRecord verifyKeyAttestation(String[] args) {
        X509Certificate[] certs = new X509Certificate[args.length];
        CertificateFactory factory;
        try {
            factory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            LOG.error("certificate factory failed", e);
            return null;
        }
        for (int i = 0; i < args.length; ++i) {
            byte[] encodedCert = Base64.decode(args[i]);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(encodedCert);
            try {
                certs[i] = (X509Certificate) factory.generateCertificate(inputStream);
            } catch (CertificateException e) {
                LOG.error("certificate get failed", e);
                return null;
            }
        }

        try {
            if (!verifyCertificateChain(certs)) {
                LOG.error("certificate chain verification failed");
                return null;
            }
        } catch (InvalidKeyException | CertificateException | NoSuchAlgorithmException | NoSuchProviderException
                | SignatureException | IOException e) {
            LOG.error("verify certificate chain failed", e);
            return null;
        }

        ParsedAttestationRecord parsedAttestationRecord;
        try {
            parsedAttestationRecord = createParsedAttestationRecord(certs[0]);
        } catch (IOException e) {
            LOG.error("create parsed attestation record failed", e);
            return null;
        }

        return parsedAttestationRecord;
    }

    private static boolean verifyCertificateChain(X509Certificate[] certs) throws CertificateException,
            NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException, IOException {
        X509Certificate parent = certs[certs.length - 1];
        for (int i = certs.length - 1; i >= 0; i--) {
            X509Certificate cert = certs[i];
            // Verify that the certificate has not expired.
            cert.checkValidity();
            cert.verify(parent.getPublicKey());
            parent = cert;
            try {
                CertificateRevocationStatus certStatus = CertificateRevocationStatus
                        .fetchStatus(cert.getSerialNumber());
                if (certStatus != null) {
                    throw new CertificateException("Certificate revocation status is " + certStatus.status.name());
                }
            } catch (IOException e) {
                throw new IOException("Unable to fetch certificate status. Check connectivity.");
            }
        }

        // If the attestation is trustworthy and the device ships with hardware-
        // level key attestation, Android 7.0 (API level 24) or higher, and
        // Google Play services, the root certificate should be signed with the
        // Google attestation root key.
        X509Certificate secureRoot1 = (X509Certificate) CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(GOOGLE_ROOT_CERTIFICATE1.getBytes(UTF_8)));
        X509Certificate secureRoot2 = (X509Certificate) CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(GOOGLE_ROOT_CERTIFICATE2.getBytes(UTF_8)));
        X509Certificate secureRoot3 = (X509Certificate) CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(GOOGLE_ROOT_CERTIFICATE3.getBytes(UTF_8)));
        if (Arrays.equals(secureRoot1.getTBSCertificate(), certs[certs.length - 1].getTBSCertificate())) {
            return true;
        } else if (Arrays.equals(secureRoot2.getTBSCertificate(), certs[certs.length - 1].getTBSCertificate())) {
            return true;
        } else if (Arrays.equals(secureRoot3.getTBSCertificate(), certs[certs.length - 1].getTBSCertificate())) {
            return true;
        } else {
            for (int i = 0; i < certs.length; i++) {
                LOG.info("certificate {} is {}", i, Hex.encodeHexString(certs[i].getTBSCertificate()));
            }
            LOG.error("root certificate not equal");
            LOG.info("should be root 1 - {} or root 2 - {} or root 3 - {}",
                    Hex.encodeHexString(secureRoot1.getTBSCertificate()),
                    Hex.encodeHexString(secureRoot2.getTBSCertificate()),
                    Hex.encodeHexString(secureRoot3.getTBSCertificate()));
            LOG.info("it is {}", Hex.encodeHexString(certs[certs.length - 1].getTBSCertificate()));
            return false;
        }
    }
}