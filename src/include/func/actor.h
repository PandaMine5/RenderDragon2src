#ifndef ACTOR_HEADER_GUARD
#define ACTOR_HEADER_GUARD


// Unfinished
// bool applyDither(vec3 worldPos, vec4 clipPosition, mat4 view, mat4 invView, vec4 ditherParams1, vec4 ditherParams2)
// {
//     vec2 p = floor((((clipPosition.xyz / clipPosition.w).xy * 0.5 + 0.5) * ditherParams1.xy) / ditherParams2[0].z) * ditherParams2[0].z;

//     vec2 small  = floor(p * 0.25);
//     vec2 medium = floor(p * 0.5);
//     vec2 large  = floor(p);

//     float n = (
//         (fract(small.x  * 0.5 + small.y  * small.y  * 0.75) * 0.25 +
//          fract(medium.x * 0.5 + medium.y * medium.y * 0.75)) * 0.25 +
//          fract(large.x  * 0.5 + large.y  * large.y  * 0.75)
//     );

//     n = (n * 64.0 + 0.5) * 0.015625;

//     vec3 camDir = -normalize(vec3(view[0].z, view[1].z, view[2].z));
//     vec3 camPos = mul(invView, vec4(0.0, 0.0, 0.0, 1.0)).xyz;

//     return smoothstep(ditherParams2[0].x, ditherParams2[0].y,
//         dot(camDir, worldPos - camPos)) <= n;
// }


// Unpack albedo's UV from a packed 16-bit value
vec2 unpackAlbedo(vec2 albedo_uv) {
    uvec2 unorm_uv = uvec2(round(albedo_uv * 65535.0));

    uvec2 position = (unorm_uv << 1) & 0xFFFEu;
    uvec2 nudge    = (unorm_uv & 0x8000u) >> 13;
    vec2 unpacked  = (vec2(position + nudge) - 2.0) / 65535.0;

    return unpacked;
}


vec2 applyGlintUV(vec2 glint_uv, float offset, float rotation, vec2 scale) {
    float rcos = cos(rotation);
    float rsin = sin(rotation);

    // Pivot rotation at center 
    vec2 uv = mul(glint_uv - 0.5, mat2(rcos, -rsin, rsin, rcos));
    uv.x += offset;

    return (uv + 0.5) * scale;
}

vec4 applyGlint(vec4 albedo, vec4 layer_uv, sampler2D glintTexture, vec4 glintColor, vec4 tileLightColor) {
    vec4 layer1 = texture2D(glintTexture, fract(layer_uv.xy)).rgbr * glintColor;
    vec4 layer2 = texture2D(glintTexture, fract(layer_uv.zw)).rgbr * glintColor;
    vec4 glint  = (layer1 + layer2) * 2.3;

    glint = vec4(glint.rgb * glint.rgb, abs(glint.a)) + 
            vec4(albedo.rgb, 0.0);

    return glint;
}


vec4 applyMultiColorChange(vec4 albedo, vec3 changeColor, vec3 multiplicativeTintColor) {
    vec2 colorMask = albedo.rg;

    albedo.rgb = colorMask.rrr * changeColor.rgb;
    albedo.rgb = mix(albedo.rgb, colorMask.ggg * multiplicativeTintColor.rgb, ceil(colorMask.g));

    return albedo;
}


vec4 applyMultiTexture(sampler2D texture1, sampler2D texture2, vec2 uv, vec4 matColor) {
    vec4 tex1 = matColor * texture2D(texture1, uv);

    #ifdef MASKED_MULTITEXTURE__ON
        vec4 tex2 = texture2D(texture2, uv);
        float maskedTexture = float((tex2.r + tex2.g + tex2.b) * (1.0 - tex2.a) > 0.0);

        tex1 = mix(tex2, tex1, maskedTexture);
    #endif

    return tex1;
}


bool shouldDiscardAlpha(vec3 albedo, float alpha, float epsilon) {
    bool result = false;

    #ifdef EMISSIVE__EMISSIVE
        result = dot(vec4(albedo, alpha), vec4_splat(1.0)) < epsilon;
    #endif

    #ifdef EMISSIVE__EMISSIVE_ONLY
        result = alpha < epsilon || alpha > 1.0 - epsilon;
    #endif

    

    #if !defined(CHANGE_COLOR__OFF) && defined(EMISSIVE__OFF)
        result = alpha < epsilon;
    #endif

    #if  defined(CHANGE_COLOR__OFF) && defined(EMISSIVE__OFF)
        result = alpha < 0.5;
    #endif

    return result;
}


float applyEntityLighting(vec3 N, vec4 lightDir, float overlayAlpha) {
    #ifdef FANCY__ON
        N.y *= lightDir.w;

        const float xFactor = -0.1;
        const float yFactor =  0.275;
        const float zFactor =  0.1;

        float ambient = 0.45 + overlayAlpha * 0.35;

        float xLight = (N.x * N.x) * xFactor;
        float yLight = (1.0 + N.y) * yFactor;
        float zLight = (N.z * N.z) * zFactor;

        return ambient + xLight + yLight + zLight;
    #else
        return 1.0;
    #endif
}

#endif //