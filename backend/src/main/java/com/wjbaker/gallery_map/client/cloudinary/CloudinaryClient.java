package com.wjbaker.gallery_map.client.cloudinary;

import com.cloudinary.Cloudinary;
import com.wjbaker.gallery_map.client._shared.ClientHelper;
import com.wjbaker.gallery_map.client.cloudinary.type.GetFolderResponse;
import com.wjbaker.gallery_map.client.cloudinary.type.GetResourcesResponse;
import com.wjbaker.gallery_map.client.cloudinary.type.UploadImageResponse;
import com.wjbaker.gallery_map.core.result.Result;
import com.wjbaker.gallery_map.core.result.ResultOf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public final class CloudinaryClient {

    private final Cloudinary api;

    public CloudinaryClient(
        @Value("${cloudinary.cloud_name}") final String cloudName,
        @Value("${cloudinary.api_key}") final String apiKey,
        @Value("${cloudinary.api_secret}") final String apiSecret) {

        this.api = new Cloudinary(Map.of(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret
        ));
    }

    public ResultOf<GetFolderResponse> getFolders(final String subFolder) {
        var response = ClientHelper.DoRequest(() -> this.api.api().subFolders(subFolder, null));
        if (response.isFailure())
            return ResultOf.fromFailure(response);

        var folders = (List<Map<String, Object>>)response.getContent().get("folders");

        return ResultOf.of(new GetFolderResponse(
            folders.stream().map(x -> new GetFolderResponse.Folder(
                (String)x.get("path"),
                (String)x.get("name"))
            ).toList(),
            (int)response.getContent().get("total_count")
        ));
    }

    public ResultOf<GetResourcesResponse> getResources(final String subFolder) {
        var response = ClientHelper.DoRequest(() -> this.api.api().resources(Map.of(
            "type", "upload",
            "prefix", subFolder
        )));
        if (response.isFailure())
            return ResultOf.fromFailure(response);

        var resources = (List<Map<String, Object>>)response.getContent().get("resources");

        return ResultOf.of(new GetResourcesResponse(resources.stream().map(x -> new GetResourcesResponse.Resource(
            (String)x.get("asset_id"),
            (String)x.get("created_at"),
            (int)x.get("bytes"),
            (int)x.get("width"),
            (int)x.get("height"),
            (String)x.get("url")
        )).toList()));
    }

    public ResultOf<UploadImageResponse> uploadImage(final byte[] imageData, final String folderName, final String fileName) {
        var response = ClientHelper.DoRequest(() -> this.api.uploader().upload(imageData, Map.of(
            "public_id", fileName,
            "folder", folderName
        )));
        if (response.isFailure())
            return ResultOf.fromFailure(response);

        var resource = response.getContent();

        return ResultOf.of(new UploadImageResponse(
            (String)resource.get("secure_url"),
            (String)resource.get("public_id")
        ));
    }

    public Result deleteImage(final String assetId) {
        var response = ClientHelper.DoRequest(() -> this.api.api().deleteResources(
            Set.of(assetId),
            new HashMap<>()));
        if (response.isFailure())
            return Result.fromFailure(response);

        var deleted = (HashMap)response.getContent().get("deleted");
        var deleteResult = (String)deleted.get(assetId);
        if (!"deleted".equals(deleteResult))
            return Result.failure("Something went wrong deleting the image from Cloudinary.");

        return Result.success();
    }
}