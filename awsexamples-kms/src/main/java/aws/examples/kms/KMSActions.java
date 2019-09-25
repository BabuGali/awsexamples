package aws.examples.kms;

import java.util.List;
import java.util.function.Consumer;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.CreateKeyRequest;
import com.amazonaws.services.kms.model.CreateKeyResult;
import com.amazonaws.services.kms.model.DeleteCustomKeyStoreRequest;
import com.amazonaws.services.kms.model.KeyListEntry;
import com.amazonaws.services.kms.model.ListKeysResult;
import com.amazonaws.services.kms.model.Tag;

public class KMSActions {

	public static void main(String[] args) {

		AWSKMS kmsClient = AWSKMSClientBuilder.standard().build();

		// Get all keys
		getAllKeys(kmsClient);
		// create key
	createKMSKey(kmsClient);

		// DeleteKey
		DeleteKMSKey(kmsClient);

	}

	private static void DeleteKMSKey(AWSKMS kmsClient) {
		DeleteCustomKeyStoreRequest deleteCustomKeyStoreRequest = new DeleteCustomKeyStoreRequest();
		deleteCustomKeyStoreRequest.setCustomKeyStoreId("4cf1ebc7-5c4b-410d-bd0b-f40fe40ba7be");
		kmsClient.deleteCustomKeyStore(deleteCustomKeyStoreRequest);
	}

	private static void createKMSKey(AWSKMS kmsClient) {
		String kmsPolicy = "{\n" + "    \"Version\": \"2012-10-17\",\n" + "    \"Id\": \"key-default-1\",\n"
				+ "    \"Statement\": [\n" + "        {\n" + "            \"Sid\": \"Enable IAM User Permissions\",\n"
				+ "            \"Effect\": \"Allow\",\n" + "            \"Principal\": {\n"
				+ "                \"AWS\": \"arn:aws:iam::3111109862639:root\"\n" + "            },\n"
				+ "            \"Action\": \"kms:*\",\n" + "            \"Resource\": \"*\"\n" + "        }\n"
				+ "    ]\n" + "}";
		CreateKeyRequest request = new CreateKeyRequest().withPolicy(kmsPolicy)
				// One or more tags. Each tag consists of a tag key and a tag value.
				.withTags(new Tag().withTagKey("CreatedBy").withTagValue("ExampleUser"));
		CreateKeyResult response = kmsClient.createKey(request);
		System.out.println("Newly created ARN: " + response.getKeyMetadata().getArn());
	}

	private static void getAllKeys(AWSKMS kmsClient) {
		ListKeysResult listKeyResult = kmsClient.listKeys();
		List<KeyListEntry> list = listKeyResult.getKeys();
		System.out.println("Total Keys :" + list.size());
		list.forEach(new Consumer<KeyListEntry>() {
			public void accept(KeyListEntry kle) {
				System.out.println("key Id:" + kle.getKeyId());
				System.out.println("Key ARN:" + kle.getKeyArn());

			}
		});

	}

}
