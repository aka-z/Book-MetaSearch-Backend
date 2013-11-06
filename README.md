Book-MetaSearch-Amazon-Interface
================================

An intermediary interface web service between the Amazon Affiliates SOAP API and the Book MetaSearch app

API
---

This interface currently only has two methods. Both methods share a common response which is just a serialized version of what Amazon returns.
Depending on the item, there more or fewer fields than those shown in the example response below.

### ASIN Search

Search for items using the Amazon ASIN number.

    GET http://<hostname>:<port>/search?method=asin&q=<asin>

### ISBN Search

Search for items using the ISBN number.

    GET http://<hostname>:<port>/search?method=isbn&q=<isbn>

### Example Response

    [
        {
            asin: "B0010SKUYM",
            detailPageURL: "http://www.amazon.com/The-Name-Wind-Kingkiller-Chronicle-ebook...",
            itemLinks: {
                itemLink: [
                    {
                        description: "Technical Details",
                        url: "http://www.amazon.com/The-Name-Wind-Kingkiller-Chronicle-ebook/..."
                    },
                    {
                        description: "Add To Baby Registry",
                        url: "http://www.amazon.com/gp/registry/baby/add-item.html..."
                    },
                    {
                        description: "Add To Wedding Registry",
                        url: "http://www.amazon.com/gp/registry/wedding/add-item.html..."
                    },
                    {
                        description: "Add To Wishlist",
                        url: "http://www.amazon.com/gp/registry/wishlist/add-item.html..."
                    },
                    {
                        description: "Tell A Friend",
                        url: "http://www.amazon.com/gp/pdp/taf/B0010SKUYM%3FSubscriptionId..."
                    },
                    {
                        description: "All Customer Reviews",
                        url: "http://www.amazon.com/review/product/B0010SKUYM..."
                    },
                    {
                        description: "All Offers",
                        url: "http://www.amazon.com/gp/offer-listing/B0010SKUYM..."
                    }
                ]
            },
            itemAttributes: {
                author: [ "Patrick Rothfuss" ],
                manufacturer: "DAW",
                productGroup: "eBooks",
                title: "The Name of the Wind: The Kingkiller Chronicle: Day One"
            }
        }
    ]