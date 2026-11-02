#ifndef FOG_HEADER_GUARD
#define FOG_HEADER_GUARD

float applyFogFade(float cameraDistance, float fogStart, float fogEnd, float maxFogDistance) {
    float distance = cameraDistance / maxFogDistance;

    return saturate((distance - fogStart) / (fogEnd - fogStart));
}

float applyFogFadeAlpha(float cameraDistance, float fogStart, float fogEnd, float maxFogDistance, float fogAlpha) {
    float distance = cameraDistance / maxFogDistance;
    distance += fogAlpha;

    return saturate((distance - fogStart) / (fogEnd - fogStart));
}

#endif //