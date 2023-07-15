import typing
import logging

import hints
import adapter
from models import breed as _breed
from models import image as _image
from models import category as _category


class Client:

    def __init__(self,
                 api_key: typing.Optional[str] = "",
                 ssl_verify: typing.Optional[bool] = True,
                 logger: typing.Optional[logging.Logger] = None) -> None:
        self._adapter = adapter.Adapter("https", "api.thecatapi.com", "v1", api_key, ssl_verify, logger)

    def breed(self, id: str) -> _breed.Breed:
        response = self._adapter.get(f"breeds/{id}")
        body = self._adapter.to_json(response)
        return _breed.Breed(**body)

    def breeds(self,
               limit: typing.Optional[int] = 10,
               page: typing.Optional[int] = 0) -> list[_breed.Breed]:
        params = {
            "limit": limit,
            "page": page
        }
        response = self._adapter.get("breeds", **params)
        body = self._adapter.to_json(response)
        output = [
            _breed.Breed(**d)
            for d in body
        ]

        return output

    def category(self) -> list[_category.Category]:
        response = self._adapter.get("categories")
        body = self._adapter.to_json(response)
        output = [
            _category.Category(**d)
            for d in body
        ]

        return output

    def search_image(self,
                     size: typing.Optional[hints.ImageSize] = "med",
                     mime_types: typing.Optional[list[hints.MimeTypes]] = None,
                     format_: typing.Optional[hints.ImageFormat] = "json",
                     order: typing.Optional[hints.Order] = "ASC",
                     page: typing.Optional[int] = 1,
                     limit: typing.Optional[int] = 5,
                     category_ids: typing.Optional[list[str]] = None,
                     breed_ids: typing.Optional[list[str]] = None,
                     has_breeds: typing.Optional[bool] = False) -> list[_image.Image]:
        params = {
            "size": size,
            "mime_types": ",".join(mime_types) if mime_types else "jpg,gif,png",
            "format": format_,
            "order": order,
            "page": page,
            "limit": limit,
            "has_breeds": 1 if has_breeds else 0,
        }
        if category_ids:
            params["category_ids"] = ",".join(category_ids)
        if breed_ids:
            params["breed_ids"] = ",".join(breed_ids)
        response = self._adapter.get("images/search", **params)
        body = self._adapter.to_json(response)
        output = [
            _image.Image(**d)
            for d in body
        ]
        return output
