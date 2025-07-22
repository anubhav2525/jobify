const serverConfig = {
    apiEndpoint: String(process.env.NEXT_PUBLIC_API_ENDPOINT) || "http://localhost:8080",
    authApiEndpoint: String(process.env.NEXT_PUBLIC_AUTH_ENDPOINT) || "http://localhost:8080/auth",
    imageCDN: String(process.env.NEXT_PUBLIC_IMAGE_CDN)
}
export default serverConfig