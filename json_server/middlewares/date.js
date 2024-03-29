// date.js
module.exports = (req, res, next) => {
    if (req.method === 'POST' && !req.url.includes("/get_token")) {
        req.body.createdAt = new Date().toISOString()
    }
    // Continue to JSON Server router
    next()
}